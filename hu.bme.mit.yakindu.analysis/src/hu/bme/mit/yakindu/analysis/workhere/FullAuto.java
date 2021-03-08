package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.stext.stext.impl.VariableDefinitionImpl;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;
import org.yakindu.sct.model.sgraph.Statechart;
import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class FullAuto {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();

		EObject root = manager.loadModel("model_input/example.sct");
		
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\n" + 
				"\n" + 
				"import java.io.IOException;\n" + 
				"import java.util.Scanner;\n" + 
				"\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"public class RunStatechart {\n" + 
				"	\n" + 
				"	public static void main(String[] args) throws IOException {\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\n" + 
				"		s.setTimer(new TimerService());\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\n" + 
				"		s.init();\n" + 
				"		s.enter();\n" + 
				"		s.runCycle();\n" + 
				"		Scanner sc=new Scanner(System.in);\n" + 
				"		String st=\"\";\n" + 
				"		boolean exit=false;\n" + 
				"		while(!exit) {\n" + 
				"			System.out.print(\"Event: \");\n" + 
				"			st=sc.nextLine();\n" + 
				"			switch (st.toLowerCase()){");
		
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinitionImpl) {
				EventDefinitionImpl event = (EventDefinitionImpl) content;
				String st=event.getName().substring(0,1).toUpperCase()+event.getName().substring(1, event.getName().length());
				System.out.println(
						"			case "+"\"" + event.getName() + "\"" + ":\n" + 
						"				s.raise"  +  st  +  "();\n" + 
						"				break;"
									);
			}
			
			
		}
		System.out.println(
				"			case \"exit\":\n" + 
				"				exit=true;\n" + 
				"				sc.close();\n" + 
				"				break;\n" + 
				"			default:\n" + 
				"				System.out.println(\"Invalid argument\");\n" + 
				"				break;\n" + 
				"			}\n" + 
				"			s.runCycle();\n" + 
				"			print(s);\n" + 
				"		}\n" + 
				"		\n" + 
				"		System.exit(0);\n" + 
				"	}");
		
		System.out.println("	public static void print(IExampleStatemachine s) {");
		iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			if(content instanceof VariableDefinitionImpl) {
				VariableDefinitionImpl var = (VariableDefinitionImpl) content;
				String st=var.getName().substring(0,1).toUpperCase()+var.getName().substring(1, var.getName().length());
						 System.out.println("		System.out.println(\""+var.getName().substring(0,1).toUpperCase()+ " = \" + s.getSCInterface().get" +st+"());" );
						 }
		}
		System.out.println("	}");
		System.out.println("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
