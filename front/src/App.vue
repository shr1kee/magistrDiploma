<template>
    <div id="app">
        <div class="row">
            <div class="tabs col-sm-2">
                <a v-for="(tab) in tabs"
                   class="tab clickableElement"
                   :class="activeTab.title === tab.title ? 'active' : ''"
                   v-on:click="changeTab(tab)"
                   :key="tab.title"
                >
                    {{tab.title}}
                </a>
            </div>
            <div class="content col-sm-6">
                <div v-for="(type) in activeTab.types"
                     :key="type.title"
                     class="tile clickableElement"
                     :class="activeType.title === type.title ? 'active' : ''"
                     v-on:click="showInfo(type)"
                >
                    <font-awesome-icon :icon="getIcon(type.title)">
                    </font-awesome-icon>
                    {{type.title}}
                    {{type.value}}
                </div>
            </div>
            <div v-on:click="changeValue(activeType.registr, activeType.value)" class="tile clickableElement active col-sm-4" style="height: 100px">
                title: {{activeType.title}}
                <input type="number" v-model="activeType.value"/>
                <br/>
                <font-awesome-icon :icon="getIcon(activeType.title)">
                </font-awesome-icon>
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
            let tabs = [
                {
                    title: 'Кухня',
                    types: [
                        {
                            title: 'light',
                            registr: 11,
                            value: 45
                        },
                        {
                            title: 'water',
                            registr: 50,
                            value: 86
                        },
                        {
                            title: 'temperature',
                            registr: 24,
                            value: 100
                        }
                    ]
                },
                {
                    title: 'Гостиная',
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
                tabs: tabs,
                activeTab: activeTab,
                activeType: activeType,
                connected: false,
                received_messages:[]
            }
        },
        methods: {
            changeTab(tab) {
                this.activeTab=tab
                this.activeType=tab.types[0]
            },
            getIcon(title) {
                switch (title) {
                    case 'light':
                        return 'lightbulb'
                    case 'water':
                        return 'tint'
                    case 'temperature':
                        return 'temperature-high'
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
                        this.stompClient.subscribe('/topicFrom', tick => {
                            console.log('tick:')
                            console.log(tick)
                        })
                        this.stompClient.subscribe('/changeValue', tick => {
                            this.analyzeResponse(tick)
                        })
                    }
                )
            },
            send() {
                this.stompClient.send('/app/topicTo', 'hi from page', {})
            },
            changeValue(registr, value) {
                this.stompClient.send('/app/sendValue',  JSON.stringify({registr:registr, value:value}), {})
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
        }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        background-color: #2B2B2B;
    }

    input {
        background-color: #ffef2a;
        border-radius: 10px;
        width: 50px;
    }
    .tile {
        cursor: pointer;
        padding: 12px 24px;
        border: 2px solid #ccc;
        border-radius: 10px;
        display: inline;
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

    /* Styling for active tab */


</style>
