<template>
  <router-link
      :to="`/user/${user.id}`"
      class="px-3"
      style="text-decoration: none; color: inherit;"
  >
    <v-avatar
        v-if="user && user.userpic"
        :size="`${size || 36}px`"
    >
      <v-img
          :src="user.userpic"
          :alt="user.name"
      ></v-img>
    </v-avatar>

    <span :class="`ml-3 text-${textStyle}`">{{ userName }}</span>
  </router-link>
</template>

<script>

import {setSrcUserpic} from "../util/handleImage";

export default {
  name: 'UserLink',
  props: ['user', 'size', 'textStyle'],
  computed: {
    userName() {
      return this.user.name
    }
  },
  beforeMount() {
    const usrpic = setSrcUserpic(this.user);
    console.log(usrpic)
    this.user.userpic = usrpic
  }
}
</script>

<style scoped>
</style>