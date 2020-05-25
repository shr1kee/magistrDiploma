const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')
const CleanWebpackPlugin = require('clean-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
    entry: {
        "editor": './src/main.js'
    },
    output: {
        globalObject: 'self',
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, 'priv/front'),
        publicPath: '/front/'
    },
    devtool: 'source-map',
    devServer: {
        contentBase: path.join(__dirname, 'priv/front'),
        compress: true,
        port: 9000
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|jpg|eot|woff|woff2|svg|ttf)([\?]?.*)$/,
                loader: "file-loader"
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            }
        ]
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        },
        extensions: ['.js', '.vue']
    },
    plugins: [
        new VueLoaderPlugin()
    ]
}
