package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.impl.VariableDefinitionImpl;
import org.yakindu.sct.model.stext.stext.impl.EventDefinitionImpl;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
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
		ArrayList<State> traps=new ArrayList<State>();
		int nameCounter=0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			//System.out.println(content.getClass());
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
				if(state.getOutgoingTransitions().isEmpty()) {traps.add(state);}
				if(state.getName().length()==0) {
					System.out.println("State has no name, recommended name is:"+ "State"+nameCounter++);
				}
			}
			if(content instanceof Transition) {
				Transition transition = (Transition) content;
						 System.out.println(transition.getSource().getName()
						+" -> "
						+transition.getTarget().getName());
			}
			if(content instanceof VariableDefinitionImpl) {
				VariableDefinitionImpl transition = (VariableDefinitionImpl) content;
						 System.out.println(transition.getName());
			}
			if(content instanceof EventDefinitionImpl) {
				EventDefinitionImpl transition = (EventDefinitionImpl) content;
						 System.out.println(transition.getName());
			}
			
		}
		System.out.println("Csapd??k: ");
		for(State trap : traps)
		System.out.println(trap.getName());
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
