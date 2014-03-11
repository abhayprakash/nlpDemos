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
import java.io.PrintWriter;
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
        File inputFile = new File("C:\\Users\\Abhay Prakash\\NewsDataAnalytics\\KnowledgeBase\\headlines.txt");
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String headline;
        
        PrintWriter writer = new PrintWriter("C:\\Users\\Abhay Prakash\\NewsDataAnalytics\\KnowledgeBase\\entities.txt", "UTF-8");
        
        while ((headline = br.readLine()) != null) {
            System.out.println(headline);
            writer.println("Original : " + headline);
            //headline = "India is being forced to reconsider how it views the neglected North-east";
            Vector<String> entities = getEntities(headline);
            writer.println("Entities:");
            for(String st : entities)
            {
                writer.println("\t" + st);
            }
        }
        br.close();
        writer.close();
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
            List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
            int tokens_ListSize = tokens.size();
            
            CoreLabel token;
            for(int i = 0; i <  tokens_ListSize ; i++ )
            {
                String possibleEntity = "";
                String pos = "";
                token = tokens.get(i);
                pos = token.get(PartOfSpeechAnnotation.class);
                while(pos.equals("NNP") || pos.equals("NN")){
                    possibleEntity += " " + token.get(TextAnnotation.class);
                    i++;
                    if(i == tokens_ListSize)
                        break;
                    token = tokens.get(i);
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