/**
 * Created by Jacky.Gao on 2017-10-01.
 */
import defaultI18nJsonData from './designer.json';
import en18nJsonData from './designer_en.json';
export default function buildLocal(){
    let language=window.navigator.language || window.navigator.browserLanguage;
    if(!language){
        language='zh-cn';
    }
    language=language.toLowerCase();
    window.i18n=defaultI18nJsonData;
    if(language!=='zh-cn'){
        window.i18n=en18nJsonData;
    }
}