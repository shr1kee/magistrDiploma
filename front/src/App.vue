<template>
    <div id="app">
        <div class="row">
            <div class="tabs col-sm-2">
                <a v-for="(tab, index) in tabs"
                   class="tab yellow"
                   :class="activeTab.title === tab.title ? 'active' : ''"
                   v-on:click="activeTab=tabs[index]"
                   :key="tab.title"
                >
                    {{tab.title}}
                </a>
                <a>
                    {{received_messages[received_messages.length-1]}}
                </a>
            </div>
            <div class="content col-sm-10">
                <!--        <a class="tab"-->
                <!--           v-for="(type, index) in activeTab.types"-->
                <!--           :key="type.title"-->
                <!--           :class="activeType.title === type.title ? 'active' : ''"-->
                <!--           v-on:click="activeType=activeTab.types[index]"-->
                <!--        >-->
                <!--          {{type.title}}-->
                <!--        </a>-->
                <div class="tile" v-on:click="send()">
                    <font-awesome-icon icon="lightbulb">
                    </font-awesome-icon>
                    light
                </div>
                <div class="tile">
                    <font-awesome-icon icon="tint">
                    </font-awesome-icon>
                    water
                </div>
                <div class="tile">
                    <font-awesome-icon icon="temperature-high">
                    </font-awesome-icon>
                    temperature
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
            let tabs = [
                {
                    title: 'first',
                    types: [
                        {
                            title: 'light'
                        },
                        {
                            title: 'water'
                        }
                    ]
                },
                {
                    title: 'second',
                    types: [
                        {
                            title: 'light'
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
                            this.received_messages.push(tick.body)
                        })
                    }
                )

            },
            send() {
                this.stompClient.send('/app/topicTo', 'hi from page', {})
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

    .tile {
        cursor: pointer;
        padding: 12px 24px;
        border: 2px solid #ccc;
        border-radius: 10px;
        background-color: #ffef2a;
        display: inline;
    }

    .tabs {
        overflow: hidden;
        float: left;
        display: flex;
        flex-direction: column;
    }

    .tab {
        flex-direction: column;
        cursor: pointer;
        padding: 12px 24px;
        transition: background-color 0.2s;
        border: 1px solid #ccc;
        border-bottom: none;
        background-color: #dfd05d;
        font-weight: bold;
    }

    /* Change background color of tabs on hover */
    .tab:hover {
        background-color: #efdf49;
        color: #fff;
    }

    /* Styling for active tab */
    .tab.active {
        background-color: #ffef2a;
        color: #484848;
        border-bottom: 2px solid #fff;
        cursor: default;
    }


</style>
