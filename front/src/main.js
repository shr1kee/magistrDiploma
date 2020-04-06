import Vue from 'vue'
import App from './App.vue'
import  BootstrapVue  from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faLightbulb, faTint, faTemperatureHigh } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import {Tabs, Tab} from 'vue-tabs-component'

library.add(faLightbulb, faTint, faTemperatureHigh)
Vue.component('tabs', Tabs)
Vue.component('tab', Tab)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.config.devtools = true
new Vue({
  render: h => h(App),
}).$mount('#app')
