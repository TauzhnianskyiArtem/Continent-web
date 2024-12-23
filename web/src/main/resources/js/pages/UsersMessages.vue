<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <v-layout row>
        <v-text-field
            label="Name user"
            placeholder="Write something"
            v-model="prefixUser"
        />
      </v-layout>
      <v-layout row>
        <v-text-field
            label="Text message"
            placeholder="Write something"
            v-model="prefixText"
        />
      </v-layout>
      <message-row v-for="message in filteredMessages" :key="message.id" :message="message" :messages="messages"/>
    </v-layout>
  </v-container>
</template>

<script>
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'

export default {
  components: {
    MessageRow,
    MessageForm
  },
  props: ['messages'],
  data() {
    return {
      message: null,
      prefixUser: '',
      prefixText: ''
    }
  },
  computed: {
    filteredMessages() {
      return this.messages.filter(message => message.author.name.toLowerCase().includes(this.prefixUser.toLowerCase()) && message.text.toLowerCase().includes(this.prefixText.toLowerCase()) && message.author.id !== frontendData.profile.id)
    }
  },
  beforeMount() {
    this.messages = this.messages.filter(message => message.author.id !== frontendData.profile.id)
  }
}
</script>

<style>

</style>
