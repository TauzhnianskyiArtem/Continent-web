<template>
  <v-app>
    <v-app-bar app
               absolute
               color="indigo darken-2"
               dark
    >
      <v-toolbar-title class="text-h5">Continent</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text
             v-if="profile"
             :disabled="$route.path === '/'"
             @click="showMyMessages"
             class="ml-5"
      >
        My messages
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn text
             v-if="profile"
             :disabled="$route.path === '/messages'"
             @click="showUsersMessages"
             class="ml-5"
      >
        Users messages
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn text
             v-if="profile"
             :disabled="$route.path === '/users'"
             @click="showSearch">
        Search
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn text
             v-if="profile"
             :disabled="$route.path === '/user'"
             @click="showProfile">
        {{ profile.name }}
      </v-btn>

      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>


import {addHandler} from "../util/ws";

export default {
  methods: {
    showMyMessages() {
      this.$router.push('/')
    },
    showUsersMessages() {
      this.$router.push('/messages')
    },
    showProfile() {
      this.$router.push('/user')
    },
    showSearch() {
      this.$router.push('/users')
    }
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  created() {
    addHandler(data => {
      console.log(data)
      if (data.objectType === 'MESSAGE') {
        console.log(data)
        const index = this.messages.findIndex(item => item.id === data.body.id)
        if (index > -1)
          if (data.eventType === 'UPDATE')
            this.messages.splice(index, 1, data.body)
          else if (data.eventType === 'REMOVE')
            this.messages.splice(index, 1)
          else
            console.error(`Looks like the event type if unknown "${data.eventType}"`)
        } else if (data.objectType === "COMMENT") {
          const indMessage = this.messages.findIndex(item => item.id === data.body.message)
          if (indMessage > -1) {
            if (data.eventType === 'CREATE') {
              const comments = this.messages[indMessage].comments
              if(comments === null)
                return
              const indComment = comments.findIndex(item => item.id === data.body.id)
              if (indComment > -1) {
                comments.splice(indComment, 1, data.body)
              } else {
                comments.push(data.body)
              }
            } else if (data.eventType === 'REMOVE') {
              const comments = this.messages[indMessage].comments
              const indComment = comments.findIndex(item => item.id === data.body.id)
              if (indComment > -1) {
                comments.splice(indComment, 1)
              }
            }
          }
        }
    })
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.push('/auth')
    }
  }
}
</script>

<style>

</style>
