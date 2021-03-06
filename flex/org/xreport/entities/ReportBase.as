/**
 * Generated by Gas3 v3.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (Report.as).
 */

package org.xreport.entities {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;
    import org.granite.tide.IPropertyHolder;

    [Bindable]
    public class ReportBase extends AbstractExternalizedBean {

        public function ReportBase() {
            super();
        }

        private var _engine:int;
        private var _id:String;
        private var _name:String;
        private var _parameters:ListCollectionView;
        private var _src_type:int;
        private var _userUID:String;

        public function set engine(value:int):void {
            _engine = value;
        }
        public function get engine():int {
            return _engine;
        }

        public function set id(value:String):void {
            _id = value;
        }
        public function get id():String {
            return _id;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set parameters(value:ListCollectionView):void {
            _parameters = value;
        }
        public function get parameters():ListCollectionView {
            return _parameters;
        }

        public function set src_type(value:int):void {
            _src_type = value;
        }
        public function get src_type():int {
            return _src_type;
        }

        public function set userUID(value:String):void {
            _userUID = value;
        }
        public function get userUID():String {
            return _userUID;
        }

        public override function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _engine = input.readObject() as int;
            _id = input.readObject() as String;
            _name = input.readObject() as String;
            _parameters = input.readObject() as ListCollectionView;
            _src_type = input.readObject() as int;
            _userUID = input.readObject() as String;
        }

        public override function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject((_engine is IPropertyHolder) ? IPropertyHolder(_engine).object : _engine);
            output.writeObject((_id is IPropertyHolder) ? IPropertyHolder(_id).object : _id);
            output.writeObject((_name is IPropertyHolder) ? IPropertyHolder(_name).object : _name);
            output.writeObject((_parameters is IPropertyHolder) ? IPropertyHolder(_parameters).object : _parameters);
            output.writeObject((_src_type is IPropertyHolder) ? IPropertyHolder(_src_type).object : _src_type);
            output.writeObject((_userUID is IPropertyHolder) ? IPropertyHolder(_userUID).object : _userUID);
        }
    }
}