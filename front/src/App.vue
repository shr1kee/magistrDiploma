/* eslint-disable */
<template>
    <div id="app">
        <div class="row">
            <div class="tabs col-sm-1">
                <a v-for="(tab) in tabs"
                   class="tab clickableElement"
                   :class="activeTab.title === tab.title ? 'active' : ''"
                   v-on:click="changeTab(tab)"
                   :key="tab.title"
                >
                    {{tab.title}}
                </a>
            </div>
            <div class="col-sm-2 tile clickableElement">
                <div style="position: relative; font-size: 2rem">
                    <p>
                        Состояние
                    </p>
                    <p>
                        <font-awesome-icon :icon="getIcon('temperature')">
                        </font-awesome-icon>
                        &nbsp;{{activeTab.state.temperature}}℃
                    </p>
                    <p>
                        <font-awesome-icon :icon="getIcon('water')">
                        </font-awesome-icon>
                        &nbsp;{{activeTab.state.humidity}}%
                    </p>
                </div>
            </div>
            <div class="col-sm-7 parent">
                <div v-for="(type) in activeTab.types"
                     :key="type.title"
                     class="tile clickableElement"
                     :class="activeType.title === type.title ? 'active' : ''"
                     v-on:click="showInfo(type)"
                >
                    <font-awesome-icon :icon="getIcon(type.title)">
                    </font-awesome-icon>
                    {{getRusTitle(type.title)}}
                    <div v-if="type.title=='light'">
                        <VueSlideBar
                                v-model="type.value"
                                :data="[0, 10, 20, 30, 40]"
                                :range="[{label: 0},{label: 10}, {label: 20}, {label: 30}, {label: 40}]"
                                :processStyle="{ backgroundColor: '#d8d8d8'}"
                                @input="changeValue(type.registr, type.value)">
                            <template slot="tooltip" slot-scope="tooltip">
                                <img src="./statics/rectangle-slider.svg">
                            </template>
                        </VueSlideBar>
                    </div>
                    <div v-else-if="type.title=='temperature'">
                        <VueSlideBar
                                v-model="type.value"
                                :data="[0, 10, 20, 30, 40, 50, 60, 70]"
                                :range="[{label: 0},{label: 10}, {label: 20}, {label: 30}, {label: 40},  {label: 50},  {label: 60}]"
                                :processStyle="{ backgroundColor: '#d8d8d8'}"
                                @input="changeValue(type.registr, type.value)">
                            <template slot="tooltip" slot-scope="tooltip">
                                <img src="./statics/rectangle-slider.svg">
                            </template>
                        </VueSlideBar>

                    </div>
                    <div v-else-if="type.title=='floor'">
                        <VueSlideBar
                                v-model="type.value"
                                :data="[0, 10, 20, 30, 40, 50, 60, 70]"
                                :range="[{label: 0},{label: 10}, {label: 20}, {label: 30}, {label: 40},  {label: 50},  {label: 60}]"
                                :processStyle="{ backgroundColor: '#d8d8d8'}"
                                @input="changeValue(type.registr, type.value)">
                            <template slot="tooltip" slot-scope="tooltip">
                                <img src="./statics/rectangle-slider.svg">
                            </template>
                        </VueSlideBar>
                    </div>
                    <div v-else-if="type.title=='movement'">
                        <RockerSwitch :value="type.value"  size="medium" @change="changeToggle"/>
                        <p>Когда изменить состояние</p>
                        <DateTime v-model="type.datetime" type="datetime"></DateTime>
                    </div>

                    <div v-else>

                    </div>
                </div>
            </div>
            <div class="col-sm-2 outside">
                <div class="parent tile clickableElement">
                    <div style="position: relative; flex: 0 0 0.33">
                        <p>
                            Погода снаружи
                        </p>
                        <p>
                            <font-awesome-icon :icon="getIcon('temperature')">
                            </font-awesome-icon>
                            &nbsp;{{outside.temperature}}℃
                        </p>
                        <p>
                            <font-awesome-icon :icon="getIcon('water')">
                            </font-awesome-icon>
                            &nbsp;{{outside.humidity}}%
                        </p>
                        <p>
                            <font-awesome-icon :icon="getIcon('wind')">
                            </font-awesome-icon>
                            &nbsp;{{outside.wind}}км/ч
                        <p/>
                    </div>
                    <div style="position:relative; font-size: 4rem; display:flex; align-items: center">
                        <font-awesome-icon :icon="getIcon('outside')">
                        </font-awesome-icon>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import SockJS from "sockjs-client"
    import Stomp from "webstomp-client"

    export default {
        name: 'App',
        components: {},
        data() {
            let outside = {
                temperature: 24,
                humidity: 34,
                wind: 3

            }
            let tabs = [
                {
                    title: 'Кухня',
                    state: {
                        temperature: 25,
                        humidity: 25
                    },
                    types: [
                        {
                            rusTitle: 'Свет',
                            title: 'light',
                            registr: 11,
                            value: 45
                        },
                        {
                            rusTitle: 'Датчик движения',
                            title: 'movement',
                            registr: 121,
                            value: true,
                            datetime: ''
                        },
                        // {
                        //     title: 'water',
                        //     registr: 50,
                        //     value: 86
                        // },
                        {
                            rusTitle: 'Температура',
                            title: 'temperature',
                            registr: 24,
                            value: 100
                        },
                        {
                            title: 'floor',
                            registr: 24,
                            value: 100
                        }
                    ]
                },
                {
                    title: 'Гостиная',
                    state: {
                        temperature: 20,
                        humidity: 20
                    },
                    types: [
                        {
                            title: 'light',
                            reginstr: 50,
                            value: 11
                        }
                    ]
                }
            ]
             let activeTab = tabs[0]
             let activeType = activeTab.types[0]
            return {
                outside: outside,
                changed: false,
                tabs: tabs,
                activeTab: activeTab,
                activeType: activeType,
                connected: false,
                received_messages:[]
            }
        },
        methods: {
            changeToggle(val) {
                this.activeType.value = val
            },
            changeTab(tab) {
                this.activeTab=tab
                this.activeType=tab.types[0]
                this.$nextTick(() => {
                    this.$children.forEach(ch => {
                        if (ch.refresh) {
                            ch.refresh()
                        }
                    })
                });
            },
            getRusTitle(title) {
                switch(title) {
                    case 'light':
                        return 'Свет'
                    case 'water':
                        return 'Вода'
                    case 'temperature':
                        return 'Температура'
                    case 'floor':
                        return 'Теплый пол'
                    case 'outside':
                        return 'Улица'
                    case 'wind':
                        return 'Ветер'
                    case 'movement':
                        return 'Датчик движения'
                }
            },
            getIcon(title) {
                switch (title) {
                    case 'light':
                        return 'lightbulb'
                    case 'water':
                        return 'tint'
                    case 'temperature':
                        return 'temperature-high'
                    case 'floor':
                        return 'border-all'
                    case 'outside':
                        return 'cloud-sun'
                    case 'wind':
                        return 'wind'
                    case 'movement':
                        return 'male'
                }
            },
            connect() {
                this.socket = new SockJS('http://localhost:8081/data')
                this.stompClient = Stomp.over(this.socket)
                this.stompClient.connect(
                    {},
                    frame => {
                        this.connected = true
                        console.log('frame:')
                        console.log(frame)
                        this.stompClient.subscribe('/startAnswer', tick => {
                            this.initialize(tick)
                            this.changed = 1
                        })
                        this.stompClient.subscribe('/changeValue', tick => {
                            this.analyzeResponse(tick)
                        })
                        this.stompClient.send('/app/startInitialize', JSON.stringify({}), {})
                    }
                )
            },
            changeValue(registr, value) {
                if (this.changed) {
                    this.stompClient.send('/app/sendValue',  JSON.stringify({registr:registr, value:value}), {})
                }
            },
            initialize(tick) {
                let data = JSON.parse(tick.body)
                let index = 0
                for (let i in this.tabs) {
                    let tab = this.tabs[i]
                    for (let j in tab.types) {
                        let type = tab.types[j]
                        type.value = data[index++]
                    }
                }
            },
            analyzeResponse(tick) {
                console.log(tick)
            },
            showInfo(type) {
                this.activeType = type
            }
        },
        mounted() {
            this.connect()
        },
    }
</script>

<style>
    html, body, #app {
        height: 100%;
    }

    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        background-color: #2B2B2B;
    }

    input {
        background-color: #ffef2a;
        border-radius: 10px;
    }
    .tile {
        cursor: pointer;
        padding: 12px 24px;
        border: 2px solid #ccc;
        border-radius: 10px;
        flex: 1 1 0px;
    }

    .tabs {
        overflow: hidden;
        float: left;
        display: flex;
        flex-direction: column;
    }

    .clickableElement {
        background-color: #dfd05d;
        border: 1px solid #ccc;
    }

    .clickableElement:hover {
        background-color: #efdf49;
        color: #fff;
    }
    .clickableElement.active {
        background-color: #ffef2a;
        color: #484848;
        border-bottom: 2px solid #fff;
        cursor: default;
    }
    .tab {
        flex-direction: column;
        cursor: pointer;
        padding: 12px 24px;
        transition: background-color 0.2s;
        border-bottom: none;
        font-weight: bold;
    }
    .parent {
        display: flex;
        flex-direction: row;
    }
    /* Styling for active tab */


</style>
