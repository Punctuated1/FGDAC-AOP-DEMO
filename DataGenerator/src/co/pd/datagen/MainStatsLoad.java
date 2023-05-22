package co.pd.datagen;

public class MainStatsLoad {

	public static void main(String[] args) {
//		LogicStatsLoad logicStatsLoad = new LogicStatsLoad();
//		logicStatsLoad.doIt();
		String template = "Select cag FROM bench.security_control WHERE  cag IN(%s) AND security_scope IN (%s)";
		String parm1 = "\"1.2.3\",\"1.2.4\"";
		String parm2 = "\"BCBSIL^STD\",\"BCBSMN^STD\"";
		String message = String.format(template, parm1,parm2);
		System.out.println(message);
	}

}
