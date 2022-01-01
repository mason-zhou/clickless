<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ClicklessGit',
  data() {
    return {
      url: 'http://gitlab.o-ibp.com:9090/mason.zhou/clickless'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
