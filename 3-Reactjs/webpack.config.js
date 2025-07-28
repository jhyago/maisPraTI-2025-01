const HtmlWebpackPlugin = require("html-webpack-plugin")
const path = require("path")

module.exports = {
    entry: "./src/index.js",
    mode: "development",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "public"),
        clean: true
    },

    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                use: "babel-loader"
            }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin ({
            template: path.resolve("./public.index.html"),
        })
    ],

    devServer: {
        port: 3000,
        static: {
            directory: path.join(__dirname, "public")
        }
    },
}