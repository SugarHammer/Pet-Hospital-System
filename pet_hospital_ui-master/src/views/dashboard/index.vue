<template>
  <div class="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
</template>

<script>

export default {
  name: 'Dashboard',
  data() {
    return {
      imgSrc: require('../../assets/preview.jpg')
    }
  }
}
</script>

<style lang="scss" scoped>
.background{
  width: 100%;
  height: 100%;  /**宽高100%是为了图片铺满屏幕 */
  z-index:-1;
  position: absolute;
}
</style>
