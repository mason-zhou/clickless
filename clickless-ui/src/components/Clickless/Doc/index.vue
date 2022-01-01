<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ClicklessDoc',
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
