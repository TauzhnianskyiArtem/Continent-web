import Vue from 'vue'

const comments = Vue.resource('/api/comments{/id}')
const messageComments = Vue.resource('/api/comments{/id}/message{/messageId}')

export default {
    add: (comment) => comments.save({}, comment),
    remove: (id, messageId) => messageComments.remove({id, messageId}),
}