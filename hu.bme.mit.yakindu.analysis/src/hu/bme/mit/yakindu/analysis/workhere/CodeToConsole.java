package hu.bme.mit.yakindu.analysis.workhere;



import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import org.yakindu.sct.model.stext.stext.impl.VariableDefinitionImpl;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class CodeToConsole {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		System.out.println("public static void print(IExampleStatemachine s) {");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			if(content instanceof VariableDefinitionImpl) {
				VariableDefinitionImpl var = (VariableDefinitionImpl) content;
						 System.out.println("System.out.println(\"W = \" + s.getSCInterface().get" +var.getName()+"());" );
			}
			if(content instanceof EventDefinitionImpl) {
				EventDefinitionImpl event = (EventDefinitionImpl) content;
				System.out.println("System.out.println(\"W = \" + s.getSCInterface().get" +event.getName()+"());"  );
			}
			
		}
		System.out.println("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
