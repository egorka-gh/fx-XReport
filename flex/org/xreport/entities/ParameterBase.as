/**
 * Generated by Gas3 v3.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (Parameter.as).
 */

package org.xreport.entities {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;
    import org.granite.tide.IPropertyHolder;

    [Bindable]
    public class ParameterBase extends AbstractExternalizedBean {

        public function ParameterBase() {
            super();
        }

        private var _id:String;
        private var _itemsInt:ListCollectionView;
        private var _keepTime:Boolean;
        private var _listSql:String;
        private var _listType:int;
        private var _multiSelect:int;
        private var _name:String;
        private var _src_type:int;
        private var _valDate:Date;
        private var _valFrom:Date;
        private var _valInt:int;
        private var _valString:String;
        private var _valTo:Date;

        public function set id(value:String):void {
            _id = value;
        }
        public function get id():String {
            return _id;
        }

        public function set itemsInt(value:ListCollectionView):void {
            _itemsInt = value;
        }
        public function get itemsInt():ListCollectionView {
            return _itemsInt;
        }

        public function set keepTime(value:Boolean):void {
            _keepTime = value;
        }
        public function get keepTime():Boolean {
            return _keepTime;
        }

        public function set listSql(value:String):void {
            _listSql = value;
        }
        public function get listSql():String {
            return _listSql;
        }

        public function set listType(value:int):void {
            _listType = value;
        }
        public function get listType():int {
            return _listType;
        }

        public function set multiSelect(value:int):void {
            _multiSelect = value;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set src_type(value:int):void {
            _src_type = value;
        }
        public function get src_type():int {
            return _src_type;
        }

        public function set valDate(value:Date):void {
            _valDate = value;
        }
        public function get valDate():Date {
            return _valDate;
        }

        public function set valFrom(value:Date):void {
            _valFrom = value;
        }
        public function get valFrom():Date {
            return _valFrom;
        }

        public function set valInt(value:int):void {
            _valInt = value;
        }
        public function get valInt():int {
            return _valInt;
        }

        public function set valString(value:String):void {
            _valString = value;
        }
        public function get valString():String {
            return _valString;
        }

        public function set valTo(value:Date):void {
            _valTo = value;
        }
        public function get valTo():Date {
            return _valTo;
        }

        public override function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _id = input.readObject() as String;
            _itemsInt = input.readObject() as ListCollectionView;
            _keepTime = input.readObject() as Boolean;
            _listSql = input.readObject() as String;
            _listType = input.readObject() as int;
            _multiSelect = input.readObject() as int;
            _name = input.readObject() as String;
            _src_type = input.readObject() as int;
            _valDate = input.readObject() as Date;
            _valFrom = input.readObject() as Date;
            _valInt = input.readObject() as int;
            _valString = input.readObject() as String;
            _valTo = input.readObject() as Date;
        }

        public override function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject((_id is IPropertyHolder) ? IPropertyHolder(_id).object : _id);
            output.writeObject((_itemsInt is IPropertyHolder) ? IPropertyHolder(_itemsInt).object : _itemsInt);
            output.writeObject((_keepTime is IPropertyHolder) ? IPropertyHolder(_keepTime).object : _keepTime);
            output.writeObject((_listSql is IPropertyHolder) ? IPropertyHolder(_listSql).object : _listSql);
            output.writeObject((_listType is IPropertyHolder) ? IPropertyHolder(_listType).object : _listType);
            output.writeObject((_multiSelect is IPropertyHolder) ? IPropertyHolder(_multiSelect).object : _multiSelect);
            output.writeObject((_name is IPropertyHolder) ? IPropertyHolder(_name).object : _name);
            output.writeObject((_src_type is IPropertyHolder) ? IPropertyHolder(_src_type).object : _src_type);
            output.writeObject((_valDate is IPropertyHolder) ? IPropertyHolder(_valDate).object : _valDate);
            output.writeObject((_valFrom is IPropertyHolder) ? IPropertyHolder(_valFrom).object : _valFrom);
            output.writeObject((_valInt is IPropertyHolder) ? IPropertyHolder(_valInt).object : _valInt);
            output.writeObject((_valString is IPropertyHolder) ? IPropertyHolder(_valString).object : _valString);
            output.writeObject((_valTo is IPropertyHolder) ? IPropertyHolder(_valTo).object : _valTo);
        }
    }
}