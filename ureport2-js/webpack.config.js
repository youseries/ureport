/**
 * Created by Jacky.gao on 2016/5/17.
 */
var webpack = require('webpack');
module.exports = {
    entry: {
        designer:'./src/index.js',
        searchform:'./src/form/index.js',
        preview:'./src/preview.js'
    },
    output: {
        path: '../ureport2-console/src/main/resources/ureport-asserts/js',
        filename: '[name].bundle.js'
    },
    module: {
        loaders: [
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
            },
            {
                test: /\.json$/,
                loader: 'json-loader'
            }
        ]
    }
};