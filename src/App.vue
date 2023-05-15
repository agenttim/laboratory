<template>
  <div class="container">

    <app-map></app-map>

    <app-users></app-users>

  </div>

</template>

<script>

import AppMap from "@/main/resources/vue/component/AppMap";
import AppUsers from "@/main/resources/vue/component/AppUsers";

export default {
  components: {AppMap, AppUsers}
}
</script>

<style>

</style>