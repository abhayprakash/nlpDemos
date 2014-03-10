/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knowledgebasecreator;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author Abhay Prakash
 */
public class KnowledgeBaseCreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String headLine = "Preeti not insecure of Abhay Deol romancing other female actors";
        Vector<String> entities = getEntities(headLine);
        System.out.println("Entities:");
        for(String st : entities)
        {
            System.out.println(st);
        }
    }
    
    static Vector<String> getEntities(String s)
    {
        Vector<String> toret = new Vector<>();
        Properties props = new Properties();
        props.put("annotators","tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        Annotation document = new Annotation(s);
        pipeline.annotate(document);
        
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences)
        {
            for(CoreLabel token : sentence.get(TokensAnnotation.class))
            {
                String possibleEntity = "";// = token.get(TextAnnotation.class);
                String pos = token.get(PartOfSpeechAnnotation.class);
                while(pos.equals("NNP") || pos.equals("NN"))
                {
                    System.out.println(token.get(TextAnnotation.class));
                    possibleEntity += token.get(TextAnnotation.class);
                    
                    token : sentence.get(TokensAnnotation.class);
                    pos = token.get(PartOfSpeechAnnotation.class);
                }
                if(!possibleEntity.equals(""))
                {
                    toret.add(possibleEntity);
                }
            }
        }
        return toret;
    }
}

/*
        String word = token.get(TextAnnotation.class);
        // this is the POS tag of the token
        String pos = token.get(PartOfSpeechAnnotation.class);
        // this is the NER label of the token
        String ne = token.get(NamedEntityTagAnnotation.class);
        
        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
      }

      // this is the parse tree of the current sentence
      Tree tree = sentence.get(TreeAnnotation.class);
      System.out.println("parse tree:\n" + tree);

      // this is the Stanford dependency graph of the current sentence
      SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
      System.out.println("dependency graph:\n" + dependencies);
    }

    // This is the coreference link graph
    // Each chain stores a set of mentions that link to each other,
    // along with a method for getting the most representative mention
    // Both sentence and token offsets start at 1!
    Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class); 
  }
}
*/