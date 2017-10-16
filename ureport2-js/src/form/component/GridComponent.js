/**
 * Created by Jacky.Gao on 2017-10-15.
 */
import GridProperty from '../property/GridProperty.js';
import Component from './Component.js';
export default class GridComponent extends Component{
    constructor(options){
        super(options);
        this.property=GridComponent.property;
    }
}
GridComponent.property=new GridProperty();