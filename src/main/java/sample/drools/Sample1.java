package sample.drools;


import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.InputStream;
import java.math.BigDecimal;

public class Sample1 {
    public static void main(String[] args){
        String ruleFle = "/sample/drools/rule/sample1.drl";

        Sample1 sample = new Sample1();
        KieSession kie = sample.loadRule(ruleFle);
        Product gold = sample.createSampleProduct("gold");
        Product diamond = sample.createSampleProduct("diamond");
        Product bitcoin = sample.createSampleProduct("bitcoin");
        sample.execute(gold,kie);
        sample.execute(diamond,kie);
        sample.execute(bitcoin,kie);
    }

    private KieSession loadRule(String ruleFile){
        KnowledgeBuilder ruleBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
        ruleBuilder.add(ResourceFactory.newInputStreamResource(resourceAsStream) , ResourceType.DRL );
        return ruleBuilder.newKieBase().newKieSession();
    }

    private Product createSampleProduct(String type){
        Product product = new Product();
        product.setType(type);
        return product;
    }

    private void execute(Product product , KieSession kie) {
        kie.insert(product);
        kie.fireAllRules();
        System.out.println(product);
    }

    public class Product{
        private String type;
        private BigDecimal discount;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }

        public String toString(){
            return String.format("Product %s %s",type,discount);
        }
    }
}
