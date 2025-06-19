package com.fjp.util;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MinioUtil {

	@Autowired
	private MinioClient minioClient;

	private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;

	/**
	 * 检查存储桶是否存在
	 *
	 * @param bucketName 存储桶名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public boolean bucketExists(String bucketName) throws InvalidKeyException, ErrorResponseException,
			IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException,
			InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
		boolean flag = minioClient.bucketExists(bucketName);
		if (flag) {
			return true;
		}
		return false;
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 * @throws RegionConflictException
	 */
	public boolean makeBucket(String bucketName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException, RegionConflictException {
		boolean flag = bucketExists(bucketName);
		if (!flag) {
			minioClient.makeBucket(bucketName);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 列出所有存储桶名称
	 *
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public List<String> listBucketNames() throws InvalidKeyException, ErrorResponseException, IllegalArgumentException,
			InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
			NoSuchAlgorithmException, XmlParserException, IOException {
		List<Bucket> bucketList = listBuckets();
		List<String> bucketListName = new ArrayList<>();
		for (Bucket bucket : bucketList) {
			bucketListName.add(bucket.name());
		}
		return bucketListName;
	}

	/**
	 * 列出所有存储桶
	 *
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public List<Bucket> listBuckets() throws InvalidKeyException, ErrorResponseException, IllegalArgumentException,
			InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
			NoSuchAlgorithmException, XmlParserException, IOException {
		return minioClient.listBuckets();
	}

	/**
	 * 删除存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public boolean removeBucket(String bucketName) throws InvalidKeyException, ErrorResponseException,
			IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException,
			InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			Iterable<Result<Item>> myObjects = listObjects(bucketName);
			for (Result<Item> result : myObjects) {
				Item item = result.get();
				// 有对象文件，则删除失败
				if (item.size() > 0) {
					return false;
				}
			}
			// 删除存储桶，注意，只有存储桶为空时才能删除成功。
			minioClient.removeBucket(bucketName);
			flag = bucketExists(bucketName);
			if (!flag) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 列出存储桶中的所有对象名称
	 *
	 * @param bucketName 存储桶名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public List<String> listObjectNames(String bucketName) throws InvalidKeyException, ErrorResponseException,
			IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException,
			InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
		List<String> listObjectNames = new ArrayList<>();
		boolean flag = bucketExists(bucketName);
		if (flag) {
			Iterable<Result<Item>> myObjects = listObjects(bucketName);
			for (Result<Item> result : myObjects) {
				Item item = result.get();
				listObjectNames.add(item.objectName());
			}
		}
		return listObjectNames;
	}

	/**
	 * 列出存储桶中的所有对象
	 *
	 * @param bucketName 存储桶名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public Iterable<Result<Item>> listObjects(String bucketName) throws InvalidKeyException, ErrorResponseException,
			IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException,
			InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			return minioClient.listObjects(bucketName);
		}
		return null;
	}

	/**
	 * 通过文件上传到对象
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param fileName   File name
	 * @return
	 * @throws InvalidKeyException
	 * @throws ErrorResponseException
	 * @throws IllegalArgumentException
	 * @throws InsufficientDataException
	 * @throws InternalException
	 * @throws InvalidBucketNameException
	 * @throws InvalidResponseException
	 * @throws NoSuchAlgorithmException
	 * @throws XmlParserException
	 * @throws IOException
	 */
	public boolean putObject(String bucketName, String objectName, String fileName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			minioClient.putObject(bucketName, objectName, fileName, null);
			ObjectStat statObject = statObject(bucketName, objectName);
			if (statObject != null && statObject.length() > 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 通过InputStream上传对象
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param stream     要上传的流
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public boolean putObject(String bucketName, String objectName, InputStream stream)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			minioClient.putObject(bucketName, objectName, stream, new PutObjectOptions(stream.available(), -1));
			ObjectStat statObject = statObject(bucketName, objectName);
			if (statObject != null && statObject.length() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 以流的形式获取一个文件对象
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public InputStream getObject(String bucketName, String objectName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			ObjectStat statObject = statObject(bucketName, objectName);
			if (statObject != null && statObject.length() > 0) {
				InputStream stream = minioClient.getObject(bucketName, objectName);
				return stream;
			}
		}
		return null;
	}

	/**
	 * 以流的形式获取一个文件对象（断点下载）
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param offset     起始字节的位置
	 * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public InputStream getObject(String bucketName, String objectName, long offset, Long length)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			ObjectStat statObject = statObject(bucketName, objectName);
			if (statObject != null && statObject.length() > 0) {
				InputStream stream = minioClient.getObject(bucketName, objectName, offset, length);
				return stream;
			}
		}
		return null;
	}

	/**
	 * 下载并将文件保存到本地
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param fileName   File name
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public boolean getObject(String bucketName, String objectName, String fileName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			ObjectStat statObject = statObject(bucketName, objectName);
			if (statObject != null && statObject.length() > 0) {
				minioClient.getObject(bucketName, objectName, fileName);
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除一个对象
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public boolean removeObject(String bucketName, String objectName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			minioClient.removeObject(bucketName, objectName);
			return true;
		}
		return false;

	}

	/**
	 * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
	 *
	 * @param bucketName  存储桶名称
	 * @param objectNames 含有要删除的多个object名称的迭代器对象
	 * @return
	 * @throws InvalidKeyException
	 * @throws ErrorResponseException
	 * @throws IllegalArgumentException
	 * @throws InsufficientDataException
	 * @throws InternalException
	 * @throws InvalidBucketNameException
	 * @throws InvalidResponseException
	 * @throws NoSuchAlgorithmException
	 * @throws XmlParserException
	 * @throws IOException
	 */
	public List<String> removeObject(String bucketName, List<String> objectNames)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		List<String> deleteErrorNames = new ArrayList<>();
		boolean flag = bucketExists(bucketName);
		if (flag) {
			Iterable<Result<DeleteError>> results = minioClient.removeObjects(bucketName, objectNames);
			for (Result<DeleteError> result : results) {
				DeleteError error = result.get();
				deleteErrorNames.add(error.objectName());
			}
		}
		return deleteErrorNames;
	}

	/**
	 * 生成一个给HTTP GET请求用的presigned URL。
	 * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 * @throws InvalidExpiresRangeException
	 */
	public String presignedGetObject(String bucketName, String objectName, Integer expires)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException, InvalidExpiresRangeException {
		boolean flag = bucketExists(bucketName);
		String url = "";
		if (flag) {
			if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
				throw new InvalidExpiresRangeException(expires,
						"expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
			}
			url = minioClient.presignedGetObject(bucketName, objectName, expires);
		}
		return url;
	}

	/**
	 * 生成一个给HTTP PUT请求用的presigned URL。
	 * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
	 * @return
	 * @throws InvalidKeyException
	 * @throws ErrorResponseException
	 * @throws IllegalArgumentException
	 * @throws InsufficientDataException
	 * @throws InternalException
	 * @throws InvalidBucketNameException
	 * @throws InvalidResponseException
	 * @throws NoSuchAlgorithmException
	 * @throws XmlParserException
	 * @throws IOException
	 * @throws InvalidExpiresRangeException
	 */
	public String presignedPutObject(String bucketName, String objectName, Integer expires)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException, InvalidExpiresRangeException {
		boolean flag = bucketExists(bucketName);
		String url = "";
		if (flag) {
			if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
				throw new InvalidExpiresRangeException(expires,
						"expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
			}
			url = minioClient.presignedPutObject(bucketName, objectName, expires);
		}
		return url;
	}

	/**
	 * 获取对象的元数据
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public ObjectStat statObject(String bucketName, String objectName)
			throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException,
			InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException,
			XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		if (flag) {
			ObjectStat statObject = minioClient.statObject(bucketName, objectName);
			return statObject;
		}
		return null;
	}

	/**
	 * 文件访问路径
	 *
	 * @param bucketName 存储桶名称
	 * @param objectName 存储桶里的对象名称
	 * @return
	 * @throws IOException
	 * @throws XmlParserException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidResponseException
	 * @throws InvalidBucketNameException
	 * @throws InternalException
	 * @throws InsufficientDataException
	 * @throws IllegalArgumentException
	 * @throws ErrorResponseException
	 * @throws InvalidKeyException
	 */
	public String getObjectUrl(String bucketName, String objectName) throws InvalidKeyException, ErrorResponseException,
			IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException,
			InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
		boolean flag = bucketExists(bucketName);
		String url = "";
		if (flag) {
			url = minioClient.getObjectUrl(bucketName, objectName);
		}
		return url;
	}

}
