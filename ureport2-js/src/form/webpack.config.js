/**
 * Created by Jacky.gao on 2016/5/17.
 */
var webpack = require('webpack');
module.exports = {
    entry: {
        test:'./test.js'
    },
    output: {
        path: './output',
        filename: '[name].bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.json$/,
                loader: 'json'
            },
            {
                test: /\.(jsx|js)?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel',
                query: {
                    presets: ['react', 'es2015'],
                    compact:true
                }
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
            {
                test: /\.(eot|woff|woff2|ttf|svg|png|jpg)$/,
                loader: 'url-loader?limit=1000000&name=[name]-[hash].[ext]'
            }
        ]
    }
};