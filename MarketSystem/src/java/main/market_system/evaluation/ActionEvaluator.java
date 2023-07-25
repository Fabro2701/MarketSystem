package market_system.evaluation;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ActionEvaluator {
	
	JSONArray program;
	public static Environment globalEnv;
	static {
		globalEnv = new Environment(null);
	}
	public static Object getGlobalVariable(String id) {
		try {
			return globalEnv.search(id);
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	boolean debug = !true;
	public ActionEvaluator(JSONArray program) {
		this.program = program;
		if(debug)System.out.println(program.toString(4));
	}
	boolean returnBoolean = false;
	public Object evaluate(Environment env, boolean clear) throws EvaluationException {
		returnBoolean = false;
		Object r = null;
		JSONObject expression = null;
		for(int i=0;i<program.length();i++) {
			expression = program.getJSONObject(i);
			try {
				r = this.eval(expression, env);
				if(returnBoolean)break;
			}
			catch(EvaluationException e) {
				//System.err.println("expression:\n"+expression.toString(4));
				e.printStackTrace();
				throw e;
			}
		}
		if(clear)env.clear();
		return r;
	}
	public Object evaluate(java.util.Map<String, Object>vars) throws EvaluationException {
		Environment env = new Environment(globalEnv);
		for(String key:vars.keySet()) env.define(key, vars.get(key));
		
		return evaluate(env, true);
	}
	public Object evaluatePairs(Object... args) throws IllegalArgumentException, EvaluationException{
		if(args.length%2!=0)throw new IllegalArgumentException("The arguments length is not even");
		
		java.util.Map<String, Object>vars = new java.util.HashMap<>();
		for(int i=0;i<args.length;i+=2) {
			vars.put((String)args[i], args[i+1]);
		}
		return evaluate(vars);
	}

	private Object eval(JSONObject query, Environment env) throws EvaluationException{
		Objects.requireNonNull(query);
		String type = query.getString("type");
		
		
		switch(type) {
		case "ExpressionStatement":
			return this.eval(query.getJSONObject("expression"), env);
		case "ReturnStatement":
			return this.evalReturnExpression(query, env);
		case "NewExpression":
			return this.evalNewExpression(query, env);
		case "AssignmentExpression":
			return this.evalAssignmentExpression(query, env);
		case "VariableStatement":
			return this.evalVariableStatement(query, env);
		case "MemberExpression":
			return this.evalMemberExpression(query, env);
		case "StaticExpression":
			return this.evalStaticExpression(query);
		case "CallExpression":
			return this.evalCallExpression(query, env);
		case "BlockStatement":
			return this.evalBlockStatement(query, env);
		case "ForStatement":
			return this.evalForStatement(query, env);
		case "WhileStatement":
			return this.evalWhileStatement(query, env);
		case "IfStatement":
			return this.evalIfStatement(query, env);
		case "UnaryExpression":
			return this.evalUnaryExpression(query, env);
		case "BinaryExpression":
			return this.evalBinaryExpression(query, env);
		case "LogicalExpression":
			return this.evalLogicalExpression(query, env);
		case "NumberLiteral":
			return this.evalNumberLiteral(query);
		case "StringLiteral":
			return this.evalStringLiteral(query);
		case "BooleanLiteral":
			return this.evalBooleanLiteral(query, env);
		case "EnumIdentifier":
			return this.evalEnumIdentifier(query);
		case "Identifier":
			return this.evalIdentifier(query, env);
		case "NullLiteral":
			return null;
		case "EmptyStatement":
			return null;
		default:
			throw new EvaluationException("unsupported type: "+type);
		}
	}
	private Object evalReturnExpression(JSONObject query, Environment env) throws EvaluationException {
		returnBoolean = true;
		return this.eval(query.getJSONObject("expression"), env);
	}
	private Object evalBooleanLiteral(JSONObject query, Environment env) throws EvaluationException{
		return Boolean.valueOf(query.getString("value"));
	}
	private Object evalUnaryExpression(JSONObject query, Environment env) throws EvaluationException{
		String op = query.getString("operator");
		switch(op) {
		case "!":
			return !(Boolean)this.eval(query.getJSONObject("argument"), env);
		default:
			throw new EvaluationException("Operator "+op+" is not supported yet");
		}
	}
	private Object evalStringLiteral(JSONObject query) throws EvaluationException{
		return query.getString("value");
	}
	private Object evalStaticExpression(JSONObject query) throws EvaluationException {
		String name = query.getString("value");
		
		Class<?> clazz;
		try {
			clazz = Class.forName(name);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EvaluationException("Satic Expression failed");
		}
	}
	private Object evalEnumIdentifier(JSONObject query) throws EvaluationException{
		String name = query.getString("value");
		String[] s = name.split("[\\.$]");
		String value = s[s.length-1];
		try {
			Class clazz = Class.forName(name.substring(0, name.length() - value.length()-1));
			return Enum.valueOf(clazz, value);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new EvaluationException("Enum Evaluation failed");
		}
	}
	private Object evalIdentifier(JSONObject query, Environment env) throws EvaluationException{
		return env.search(query.getString("name"));
	}
	private Object evalWhileStatement(JSONObject query, Environment env) throws EvaluationException{
		JSONObject test = query.getJSONObject("test");
		JSONObject body = query.getJSONObject("body");
		
		Object r = null;
		while((boolean)eval(test, env)) {
			r = eval(body, env);
		}
		return r;
	}
	private Object evalForStatement(JSONObject query, Environment env) throws EvaluationException{
		JSONObject init = query.has("init")?query.getJSONObject("init"):null;
		JSONObject test = query.has("test")?query.getJSONObject("test"):null;
		JSONObject update = query.has("update")?query.getJSONObject("update"):null;
		JSONObject body = query.has("body")?query.getJSONObject("body"):null;
		
		Object r = null;
		if(init!=null)eval(init, env);
		for(;test!=null?(boolean)eval(test, env):true;) {
			if(body!=null)r = eval(body, env);
			if(update!=null)eval(update, env);
		}

		return r;
	}
	private Object evalNewExpression(JSONObject query, Environment env) throws EvaluationException {
		
		JSONArray arguments = query.getJSONArray("arguments");
		Object args[] = new Object[arguments.length()];
		for(int i=0;i<arguments.length();i++) {
			args[i] = eval(arguments.getJSONObject(i), env);
			//clazzs[i] = args[i].getClass();
		}
		

		
		JSONObject callee = query.getJSONObject("callee");
		String clazzS = callee.getString("value");

		try {
			Class<?> clazz = Class.forName(clazzS);
			for(var c:clazz.getConstructors()) {
				if(c.getParameterCount()==args.length)return c.newInstance(args);
			}
			//for(var c:clazz.getConstructors())if(c.getParameterCount()==0)return c.newInstance(null);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
			throw new EvaluationException("New Expression failed");
		}
		return null;
	}
	private Object evalNumberLiteral(JSONObject query) throws EvaluationException{
		try {
			String clazzs = query.getString("class");
			Class<?>clazz = Class.forName(clazzs);
			return clazz.getMethod("valueOf", String.class).invoke(clazz, query.get("value"));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | JSONException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new EvaluationException("NumberLiteral evaluation failed");
		}
	
	}
//	private Class[] getAppropiateClasses(Executable ex, Class[]params) {
//		Class[]clazzs = ex.getParameterTypes();
//		if(clazzs.length!=params.length)return null;
//		
//		Class[]casts = new Class[clazzs.length];
//		for(int i=0;i<params.length;i++) {
//			if(clazzs[i].equals(params[i]))continue;
//			if(clazzs[i].isPrimitive()) {
//				casts[i]=
//			}
//		}
//		return null;
//	}
	private Object evalCallExpression(JSONObject query, Environment env) throws EvaluationException{
		JSONObject callee = query.getJSONObject("callee");
		JSONArray arguments = query.getJSONArray("arguments");
		
		Class<?>clazzs[] = new Class<?>[arguments.length()];
		Object args[] = new Object[arguments.length()];
		for(int i=0;i<arguments.length();i++) {
			args[i] = eval(arguments.getJSONObject(i), env);
			//clazzs[i] = args[i].getClass();
		}
		
		JSONObject property = callee.getJSONObject("property");
		JSONObject object = callee.getJSONObject("object");
		Object ob = this.eval(object, env);
		if(ob instanceof Class) {
			try {
				Method m = null;
				//Method m = ob.getClass().getMethod(property.getString("name"), clazzs);
				for(Method mi:((Class)ob).getMethods())if(mi.getName().equals(property.getString("name")))m=mi;
				Class<?>prms[] = m.getParameterTypes();
				for(int i=0;i<arguments.length();i++) {
					if(args[i] instanceof Number) {
						if(args[i].getClass()==Double.class) {
							double tmp = ((Number)args[i]).doubleValue();
							if(prms[i]==Integer.class || prms[i]==int.class) {
								args[i] = (int)tmp;
							}
						}
						//args[i] = prms[i].cast(((Number)args[i]).floatValue()) ;
					}
					else args[i] = prms[i].cast(args[i]);
				}
				return m.invoke(null, args);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("query: "+query.toString(4));
				throw new EvaluationException("Call Expression failed");
			}
		}
		else {
			try {
				if(ob==null) {
					throw new EvaluationException("Object "+ob+" is null");
				}
				Method m = null;
				//Method m = ob.getClass().getMethod(property.getString("name"), clazzs);
				for(Method mi:ob.getClass().getMethods())if(mi.getName().equals(property.getString("name")))m=mi;
				
				Class<?>prms[] = m.getParameterTypes();
				for(int i=0;i<arguments.length();i++) {
					if(args[i] instanceof Number) {
						if(args[i].getClass()==Double.class) {
							double tmp = ((Number)args[i]).floatValue();
							if(prms[i]==Integer.class || prms[i]==int.class) {
								args[i] = (int)tmp;
							}
						}
						//args[i] = prms[i].cast(((Number)args[i]).floatValue()) ;
					}
					else args[i] = prms[i].cast(args[i]);
				}
			
				return m.invoke(ob, args);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("query: "+query.toString(4));
				throw new EvaluationException("Call Expression failed");
			}
		}
		
	}
	private Object evalMemberExpression(JSONObject query, Environment env) throws EvaluationException{
		JSONObject property = query.getJSONObject("property");
		JSONObject object = query.getJSONObject("object");
		boolean computed = query.getBoolean("computed");
		if(!computed) {
			Object ob = this.eval(object, env);
			if(ob instanceof Class) {
				try {
					Field f = ((Class)ob).getField(property.getString("name"));
					return f.get(ob);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					Field f = ob.getClass().getField(property.getString("name"));
					return f.get(ob);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	private Object evalBlockStatement(JSONObject query, Environment env) throws EvaluationException{
		Object r = null;
		
		JSONArray body = query.getJSONArray("body");
		Environment newEnv = new Environment(env);
		for(int i=0;i<body.length();i++) {
			JSONObject b = body.getJSONObject(i);
			r = eval(b, newEnv);
		}
		return r;
	}
	private Object evalIfStatement(JSONObject query, Environment env) throws EvaluationException{
		JSONObject test = query.getJSONObject("test");
		JSONObject consequent = query.getJSONObject("consequent");
		JSONObject alternate = query.has("alternate")?query.getJSONObject("alternate"):null;
		
		if((boolean) eval(test, env)) {
			return eval(consequent, env);
		}
		else if(alternate!=null){
			return eval(alternate, env);
		}
		return null;
	}
	private Object evalLogicalExpression(JSONObject query, Environment env) throws EvaluationException{
		JSONObject left = query.getJSONObject("left");
		JSONObject right = query.getJSONObject("right");
		String op = query.getString("operator");
		
		switch(op) {
		case ">":
			return ((Number)eval(left, env)).doubleValue()>((Number)eval(right, env)).doubleValue();
		case "<":
			return ((Number)eval(left, env)).doubleValue()<((Number)eval(right, env)).doubleValue();
		case "==":
			Object l = eval(left, env);
			if(l instanceof Number)return ((Number)l).doubleValue()==((Number)eval(right, env)).doubleValue();
			else return l == eval(right, env);
		case "!=":
			return eval(left, env) != eval(right, env);
		case ">=":
			return ((Number)eval(left, env)).doubleValue()>=((Number)eval(right, env)).doubleValue();
		case "<=":
			return ((Number)eval(left, env)).doubleValue()<=((Number)eval(right, env)).doubleValue();
		case "&&":
			return (boolean)eval(left, env)&&(boolean)eval(right, env);
		case "||":
			return (boolean)eval(left, env)||(boolean)eval(right, env);
		default:
			throw new EvaluationException("Operator "+op+" is not supported yet");
		}
	}
	private Object evalBinaryExpression(JSONObject query, Environment env) throws EvaluationException{
		JSONObject left = query.getJSONObject("left");
		JSONObject right = query.getJSONObject("right");
		String op = query.getString("operator");
		
		switch(op) {
		case "+":
			return ((Number)eval(left, env)).doubleValue()+((Number)eval(right, env)).doubleValue();
		case "-":
			return ((Number)eval(left, env)).doubleValue()-((Number)eval(right, env)).doubleValue();
		case "*":
			return ((Number)eval(left, env)).doubleValue()*((Number)eval(right, env)).doubleValue();
		case "/":
			return ((Number)eval(left, env)).doubleValue()/((Number)eval(right, env)).doubleValue();
		case "%":
			return ((Number)eval(left, env)).intValue()%((Number)eval(right, env)).intValue();
		default:
			throw new EvaluationException("Operator "+op+" is not supported yet");
		}
	}
	private Object evalVariableStatement(JSONObject query, Environment env) throws EvaluationException{
		JSONObject declaration = query.getJSONObject("declaration");
		return env.define(declaration.getJSONObject("id").getString("name"), 
				     eval(declaration.getJSONObject("init"), env));
	}
	private Object evalAssignmentExpression(JSONObject query, Environment env) throws EvaluationException{
		JSONObject right = query.getJSONObject("right");
		JSONObject left = query.getJSONObject("left");
		String op = query.getString("operator");//only supports '='
		
		Object lefto = eval(left, env);
		Object righto = eval(right, env);
		
		if(left.has("property")) {
			try {
				Object leftF = eval(left.getJSONObject("object"), env);
				Field f = leftF.getClass().getField(left.getJSONObject("property").getString("name"));
				if(righto instanceof Number) {
					if(f.get(leftF) instanceof Integer) {
						f.setInt(leftF, ((Number)righto).intValue());
					}
					else f.setDouble(leftF, ((Number)righto).doubleValue());
				}
				else f.set(leftF,  f.getType().cast(righto));//untested
				
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		else {
			lefto = righto;//local
			env.assign(left.getString("name"), lefto);
		}
		
		
		return lefto;
	}
	public static class Test{
		public Test2 tt= new Test2();
		public int x=2;
	}
	public static class Test2{
		public int y=5;
		public int getY() {
			return y;
		}
	}
	public static void main(String args[]) {
		Test t = new Test();
		String code="t.tt.y=3+t.tt.y;t.tt.y;";
		JSONObject ob = new OOPParser().parse(code);
		
		try {
			Object r = new ActionEvaluator(ob.getJSONArray("code")).evaluatePairs("t",t);
			System.out.println(r);
		} catch (IllegalArgumentException | JSONException | EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
