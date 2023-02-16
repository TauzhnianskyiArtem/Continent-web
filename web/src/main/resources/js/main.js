import Vue from 'vue'
import Vuetify from 'vuetify'
import 'api/resource'
import App from 'pages/App.vue'
import router from 'router/router'
import * as Sentry from "@sentry/vue"
import {BrowserTracing} from "@sentry/tracing"
import 'vuetify/dist/vuetify.min.css'
import {connect} from './util/ws'


Sentry.init({
    Vue,
    dsn: "https://be0f18d77d3d4c23a246dbd6d91e5e90@o1386630.ingest.sentry.io/6707008",
    integrations: [
        new BrowserTracing({
            routingInstrumentation: Sentry.vueRouterInstrumentation(router),
            tracingOrigins: ["localhost", "continent.ga", /^\//],
        }),
    ],
    tracesSampleRate: 1.0,
});

if (frontendData.profile) {
    connect();

    Sentry.setUser({
        id: frontendData.profile & frontendData.profile.id,
        username: frontendData.profile & frontendData.profile.name
    });

}


Vue.use(Vuetify)

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    router,
    render: a => a(App)
})
