import Vue from 'vue'
import App from './App.vue'
import  BootstrapVue  from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import "vue-rocker-switch/dist/vue-rocker-switch.css"
import { library } from '@fortawesome/fontawesome-svg-core'
import { faLightbulb, faTint, faTemperatureHigh, faBorderAll, faCloudSun, faWind, faMale } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import RockerSwitch from "vue-rocker-switch"
import { Datetime } from 'vue-datetime'
// You need a specific loader for CSS files
import 'vue-datetime/dist/vue-datetime.css'

import VueSlideBar from 'vue-slide-bar'


library.add(faLightbulb, faTint, faTemperatureHigh, faBorderAll, faCloudSun, faWind, faMale)
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.component('VueSlideBar', VueSlideBar)
Vue.component('RockerSwitch', RockerSwitch)
Vue.component('DateTime', Datetime)
Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.config.devtools = true
new Vue({
  render: h => h(App),
}).$mount('#app')
