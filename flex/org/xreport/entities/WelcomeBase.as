/**
 * Generated by Gas3 v3.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (Welcome.as).
 */

package org.xreport.entities {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import org.granite.meta;
    import org.granite.tide.IEntityManager;
    import org.granite.tide.IPropertyHolder;

    use namespace meta;

    [Managed]
    public class WelcomeBase extends AbstractEntity {

        public function WelcomeBase() {
            super();
        }

        private var _message:String;
        private var _name:String;

        public function set message(value:String):void {
            _message = value;
        }
        public function get message():String {
            return _message;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        meta override function merge(em:IEntityManager, obj:*):void {
            var src:WelcomeBase = WelcomeBase(obj);
            super.meta::merge(em, obj);
            if (meta::isInitialized()) {
               em.meta_mergeExternal(src._message, _message, null, this, 'message', function setter(o:*):void{_message = o as String}, false);
               em.meta_mergeExternal(src._name, _name, null, this, 'name', function setter(o:*):void{_name = o as String}, false);
            }
        }

        public override function readExternal(input:IDataInput):void {
            super.readExternal(input);
            if (meta::isInitialized()) {
                _message = input.readObject() as String;
                _name = input.readObject() as String;
            }
        }

        public override function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            if (meta::isInitialized()) {
                output.writeObject((_message is IPropertyHolder) ? IPropertyHolder(_message).object : _message);
                output.writeObject((_name is IPropertyHolder) ? IPropertyHolder(_name).object : _name);
            }
        }
    }
}
