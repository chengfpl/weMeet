package com.tencent.weili.util;

/*
 * Finished by lifang
 */
import java.util.*;

public class Algorithm {

    static class Pair<K,V>{
        K key;
        V value;
        Pair(K key,V value){
            this.key = key;
            this.value = value;
        }
        public String toString(){
            return key.toString()+"&"+value.toString();
        }
    }

    class TimeSpec implements Comparable<TimeSpec>{
        int year,month,day,hour,minute,type;
        int user_id;
        TimeSpec end;
        @Override
        public int compareTo(TimeSpec ts2){
            if(year<ts2.year) return -1;
            if(year>ts2.year) return 1;
            if(month<ts2.month) return -1;
            if(month>ts2.month) return 1;
            if(day<ts2.day) return -1;
            if(day>ts2.day) return 1;
            if(hour<ts2.hour) return -1;
            if(hour>ts2.hour) return 1;
            if(minute<ts2.minute) return -1;
            if(minute>ts2.minute) return 1;
            //先退出再进入
            return Integer.compare(type,ts2.type);
        }
        @Override
        public boolean equals(Object ts2){
            if(ts2 instanceof TimeSpec)
                return this.compareTo((TimeSpec) ts2)==0;
            return false;
        }

        public String toString(){
            return String.format("%d-%02d-%02d %02d:%02d:00",year,month,day,hour,minute);
        }

        public TimeSpec clone(){
            TimeSpec ts = new TimeSpec();
            ts.year = year;
            ts.month = month;
            ts.day = day;
            ts.hour = hour;
            ts.minute = minute;
            ts.user_id = user_id;
            return ts;
        }
    }

    TimeSpec parse_one(String time_format,int type,int user_id){
        TimeSpec ts = new TimeSpec();
        String[] split = time_format.split(" ");
        String[] date = split[0].split("-");
        String[] time = split[1].split(":");
        ts.year= Integer.parseInt(date[0]);
        ts.month = Integer.parseInt(date[1]);
        ts.day = Integer.parseInt(date[2]);

        ts.hour=  Integer.parseInt(time[0]);
        ts.minute = Integer.parseInt(time[1]);
        ts.type = type;
        ts.user_id = user_id;
        return ts;
    }

    ArrayList<TimeSpec> parse_time(int user_id,String time){
        String[] segment = time.split(";");
        ArrayList<TimeSpec> ret = new ArrayList<>();

        for (int i=0;i<segment.length;i++){
            String[] pair = segment[i].split("_");
            TimeSpec start_time = parse_one(pair[0],0,user_id);
            start_time.end = parse_one(pair[1],1,user_id);
            ret.add(start_time);
        }

        return ret;
    }

    public static void main(String args[]) {

        // 假设数据格式和内容都是符合要求的,此处没做数据检验
        HashMap<Integer,String> input_ = new HashMap<>();
        // n hour interval
        input_.put(100,"2018-05-02 07:00:00_2018-05-02 10:00:00;2018-05-02 09:00:00_2018-05-02 13:00:00;2021-10-08 06:00:00_2021-10-08 09:00:00");
        input_.put(102,"2018-05-02 08:00:00_2018-05-02 11:00:00;2018-05-02 11:00:00_2018-05-02 14:00:00;2018-05-02 12:00:00_2018-05-02 15:00:00");
        System.out.println("Input:");
        System.out.println(input_);
        HashMap<String ,ArrayList<Integer>> res_hour = get_stat_by_one_hour(input_);
        System.out.println("Hour:");
        System.out.println(res_hour);

        HashMap<String ,ArrayList<Integer>> res_interval = get_stat_by_interval(input_,2);
        System.out.println("given interval:");
        System.out.println(res_interval);

        HashMap<String ,ArrayList<Integer>> res_day = get_stat_by_one_day(input_);
        System.out.println("Day:");
        System.out.println(res_day);

        HashMap<String ,ArrayList<Integer>> res_half = get_stat_by_half_day(input_);
        System.out.println("Half:");
        System.out.println(res_half);


    }

    private static ArrayList<TimeSpec> string_to_time_spec(HashMap<Integer,String> input_){
        Algorithm algo = new Algorithm();
        ArrayList<TimeSpec> parsed_input = new ArrayList<>();
        for(HashMap.Entry<Integer,String > entry : input_.entrySet()){
            ArrayList<TimeSpec> user_time = algo.parse_time(entry.getKey(),entry.getValue());
            parsed_input.addAll(user_time);
        }
        Collections.sort(parsed_input);
        return parsed_input;
    }

    private static HashMap<String,ArrayList<Integer>> time_spec_to_string(ArrayList<Pair<TimeSpec,ArrayList<Integer>>> input){
        HashMap<String,ArrayList<Integer>> ret = new HashMap<>();
        for (Pair<TimeSpec,ArrayList<Integer>> slot :input){
            String slot_time = slot.key.toString()+"_"+slot.key.end.toString();
            ret.put(slot_time,slot.value);
        }
        return ret;
    }


    public static HashMap<String,ArrayList<Integer>> get_stat_by_interval(HashMap<Integer,String> input_,int interval){
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan_ = _get_discrete_plan(input_);
        ArrayList<Pair<TimeSpec,HashSet<Integer>>> plan_by_one = new ArrayList<>();
        for(Pair<TimeSpec,ArrayList<Integer>> p : plan_)
            plan_by_one.add(new Pair<>(p.key,new HashSet<>(p.value)));

        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan_by_interval = new ArrayList<>();


        for (int i=0;i<plan_by_one.size();++i){
            TimeSpec ts = plan_by_one.get(i).key;
            HashSet<Integer> hs =  plan_by_one.get(i).value;
            HashSet<Integer> rest = new HashSet<>(hs);
            int probe = i+1;
            while(probe<plan_by_one.size()){
                TimeSpec tp = plan_by_one.get(probe).key;
                int offset = tp.end.hour - ts.hour;
                if(offset<interval) {
                    rest.retainAll(plan_by_one.get(probe).value);
                }
                else if(offset == interval){
                    rest.retainAll(plan_by_one.get(probe).value);
                    plan_by_interval.add(new Pair<>(
                            DiscreteTime(ts,ts.hour,ts.hour+interval),
                            new ArrayList<>(rest)));
                }
                if(offset >=interval) break;
                probe++;
            }

        }

        return time_spec_to_string(plan_by_interval);
    }

    private static ArrayList<Pair<TimeSpec,ArrayList<Integer>>> _get_discrete_plan(HashMap<Integer,String> input_){
        ArrayList<TimeSpec> parsed_input = string_to_time_spec(input_);
        ArrayList<TimeSpec> parsed_split = new ArrayList<>();
        for (TimeSpec ts : parsed_input){
            int delta = ts.end.hour - ts.hour;
            for (int i = 0;i<delta;++i){
                TimeSpec start = ts.clone();
                TimeSpec end = ts.clone();
                start.hour += i;
                end.hour = start.hour + 1;
                start.end = end;
                parsed_split.add(start);
            }
        }
        Collections.sort(parsed_split);
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan = get_plan(parsed_split);
        for (Pair<TimeSpec,ArrayList<Integer>> pair : plan){
            pair.value = unique(pair.value);
        }
        return plan;
    }

    public static HashMap<String,ArrayList<Integer>> get_stat_by_one_hour(HashMap<Integer,String> input_){
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan = _get_discrete_plan(input_);
        return time_spec_to_string(plan);

    }

    private static <T> ArrayList<T> unique(ArrayList<T> arr){
        HashSet<T> hs = new HashSet<T>(arr);
        return  new ArrayList<T>(hs);
    }
    public static HashMap<String,ArrayList<Integer>> get_stat_by_one_day(HashMap<Integer,String> input_) {
        ArrayList<TimeSpec> parsed_input = string_to_time_spec(input_);
        for (TimeSpec ts : parsed_input){
            ts.hour = ts.minute = ts.end.minute = 0;
            ts.end.hour = 24;
        }
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan = get_plan(parsed_input);

        for (Pair<TimeSpec,ArrayList<Integer>> pair : plan){
            pair.value = unique(pair.value);
        }
        return time_spec_to_string(plan);
    }

    static TimeSpec DiscreteTime(TimeSpec base,int start_hour,int end_hour) {
        TimeSpec tmp_start = base.clone();
        TimeSpec tmp_end = base.clone();
        tmp_start.hour = start_hour;
        tmp_start.minute = tmp_end.minute = 0;
        tmp_end.hour = end_hour;
        tmp_start.end = tmp_end;
        return tmp_start;
    }

    static void AddTime(TimeSpec cmp,ArrayList<TimeSpec> target) {

        if (cmp.hour < 8) {
            target.add(DiscreteTime(cmp, 0, 8));
        }
        //上午
        else if (cmp.hour < 12) {
            target.add(DiscreteTime(cmp, 8, 12));
        }
        //中午
        else if (cmp.hour < 14) {
            target.add(DiscreteTime(cmp, 12, 14));
        }
        //下午
        else if (cmp.hour < 18) {
            target.add(DiscreteTime(cmp, 14, 18));
        } else {
            target.add(DiscreteTime(cmp, 18, 24));
        }

    }

    static void AddTime_if_inclusive(TimeSpec cmp,ArrayList<TimeSpec> target){
        //早上
        if (cmp.hour <= 8) {
            target.add(DiscreteTime(cmp, 0, 8));
        }
        //上午
        else if (cmp.hour <= 12) {
            target.add(DiscreteTime(cmp, 8, 12));
        }
        //中午
        else if (cmp.hour <= 14) {
            target.add(DiscreteTime(cmp, 12, 14));
        }
        //下午
        else if (cmp.hour <= 18) {
            target.add(DiscreteTime(cmp, 14, 18));
        }
        //晚上
        else {
            target.add(DiscreteTime(cmp, 18, 24));
        }
    }

    public static HashMap<String,ArrayList<Integer>> get_stat_by_half_day(HashMap<Integer,String> input_) {
        ArrayList<TimeSpec> parsed_input = string_to_time_spec(input_);
        ArrayList<TimeSpec> parsed_target = new ArrayList<>();
        for (TimeSpec ts : parsed_input){
            TimeSpec start = ts;
            TimeSpec end = ts.end;
            AddTime(start,parsed_target);
            //如果时间是闭区间[08:00:00-10:00:00] 用addTime_if_inclusive, 如果是左闭右开区间[08:00:00-09:59:59] 用addTime
            AddTime_if_inclusive(end,parsed_target);
        }
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> plan = get_plan(parsed_target);
        for (Pair<TimeSpec,ArrayList<Integer>> pair : plan){
            pair.value = unique(pair.value);
        }
        return time_spec_to_string(plan);
    }
    // 主要算法
    private static ArrayList<Pair<TimeSpec,ArrayList<Integer>>>  get_plan( ArrayList<TimeSpec> input){
        int index = 0;
        ArrayList<Pair<TimeSpec,ArrayList<Integer>>> ret = new ArrayList<>();
        while(index<input.size()){
            TimeSpec cur = input.get(index);
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(cur.user_id);
            ret.add(new Pair<>(cur,arr));

            int probe = index+1;
            // probe is index of the first timeSpec that is not equal to current timeSpec
            while(probe<input.size()){
                TimeSpec precedent = input.get(probe);
                if(precedent.equals(cur)) {
                    ret.get(ret.size()-1).value.add(precedent.user_id);
                    probe++;
                }
                else break;
            }
            // index is the next timeSpec now;
            index = probe;
        }
        return ret;
    }
}