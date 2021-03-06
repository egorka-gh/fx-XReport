/**
 * Generated by Gas3 v3.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (UkmStore.as).
 */

package org.xreport.entities {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import org.granite.tide.IPropertyHolder;

    [Bindable]
    public class UkmStoreBase extends AbstractExternalizedBean {

        public function UkmStoreBase() {
            super();
        }

        private var _inn:String;
        private var _name:String;
        private var _store_id:int;

        public function set inn(value:String):void {
            _inn = value;
        }
        public function get inn():String {
            return _inn;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set store_id(value:int):void {
            _store_id = value;
        }
        public function get store_id():int {
            return _store_id;
        }

        public override function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _inn = input.readObject() as String;
            _name = input.readObject() as String;
            _store_id = input.readObject() as int;
        }

        public override function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject((_inn is IPropertyHolder) ? IPropertyHolder(_inn).object : _inn);
            output.writeObject((_name is IPropertyHolder) ? IPropertyHolder(_name).object : _name);
            output.writeObject((_store_id is IPropertyHolder) ? IPropertyHolder(_store_id).object : _store_id);
        }
    }
}