/**
 * Generated by Gas3 v3.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (ReportSchedule.as).
 */

package org.xreport.entities {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import org.granite.tide.IPropertyHolder;

    [Bindable]
    public class ReportScheduleBase extends AbstractExternalizedBean {

        public function ReportScheduleBase() {
            super();
        }

        private var _active:Boolean;
        private var _id:int;
        private var _label:String;
        private var _last_run:Date;
        private var _report:String;
        private var _run_after_hour:int;
        private var _run_date:Date;
        private var _run_repeat:Boolean;
        private var _run_type:int;
        private var _send_to:String;
        private var _source:String;
        private var _week_day:int;

        public function set active(value:Boolean):void {
            _active = value;
        }
        public function get active():Boolean {
            return _active;
        }

        public function set id(value:int):void {
            _id = value;
        }
        public function get id():int {
            return _id;
        }

        public function set label(value:String):void {
            _label = value;
        }
        public function get label():String {
            return _label;
        }

        public function set last_run(value:Date):void {
            _last_run = value;
        }
        public function get last_run():Date {
            return _last_run;
        }

        public function set report(value:String):void {
            _report = value;
        }
        public function get report():String {
            return _report;
        }

        public function set run_after_hour(value:int):void {
            _run_after_hour = value;
        }
        public function get run_after_hour():int {
            return _run_after_hour;
        }

        public function set run_date(value:Date):void {
            _run_date = value;
        }
        public function get run_date():Date {
            return _run_date;
        }

        public function set run_repeat(value:Boolean):void {
            _run_repeat = value;
        }
        public function get run_repeat():Boolean {
            return _run_repeat;
        }

        public function set run_type(value:int):void {
            _run_type = value;
        }
        public function get run_type():int {
            return _run_type;
        }

        public function set send_to(value:String):void {
            _send_to = value;
        }
        public function get send_to():String {
            return _send_to;
        }

        public function set source(value:String):void {
            _source = value;
        }
        public function get source():String {
            return _source;
        }

        public function set week_day(value:int):void {
            _week_day = value;
        }
        public function get week_day():int {
            return _week_day;
        }

        public override function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _active = input.readObject() as Boolean;
            _id = input.readObject() as int;
            _label = input.readObject() as String;
            _last_run = input.readObject() as Date;
            _report = input.readObject() as String;
            _run_after_hour = input.readObject() as int;
            _run_date = input.readObject() as Date;
            _run_repeat = input.readObject() as Boolean;
            _run_type = input.readObject() as int;
            _send_to = input.readObject() as String;
            _source = input.readObject() as String;
            _week_day = input.readObject() as int;
        }

        public override function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject((_active is IPropertyHolder) ? IPropertyHolder(_active).object : _active);
            output.writeObject((_id is IPropertyHolder) ? IPropertyHolder(_id).object : _id);
            output.writeObject((_label is IPropertyHolder) ? IPropertyHolder(_label).object : _label);
            output.writeObject((_last_run is IPropertyHolder) ? IPropertyHolder(_last_run).object : _last_run);
            output.writeObject((_report is IPropertyHolder) ? IPropertyHolder(_report).object : _report);
            output.writeObject((_run_after_hour is IPropertyHolder) ? IPropertyHolder(_run_after_hour).object : _run_after_hour);
            output.writeObject((_run_date is IPropertyHolder) ? IPropertyHolder(_run_date).object : _run_date);
            output.writeObject((_run_repeat is IPropertyHolder) ? IPropertyHolder(_run_repeat).object : _run_repeat);
            output.writeObject((_run_type is IPropertyHolder) ? IPropertyHolder(_run_type).object : _run_type);
            output.writeObject((_send_to is IPropertyHolder) ? IPropertyHolder(_send_to).object : _send_to);
            output.writeObject((_source is IPropertyHolder) ? IPropertyHolder(_source).object : _source);
            output.writeObject((_week_day is IPropertyHolder) ? IPropertyHolder(_week_day).object : _week_day);
        }
    }
}