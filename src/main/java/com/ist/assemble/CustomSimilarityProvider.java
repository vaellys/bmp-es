package com.ist.assemble;

import org.apache.lucene.search.similarities.Similarity;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.similarity.AbstractSimilarityProvider;


/**
 * 把自定义的打分机制类加载到elasticsearch中
 * @author qianguobing
 *
 */
public class CustomSimilarityProvider extends AbstractSimilarityProvider {
    
    private CustomSimilarity similarity;

    @Inject
    public CustomSimilarityProvider(@Assisted String name, @Assisted Settings settings) {
        super(name);
        this.similarity = new CustomSimilarity();
    }

    @Override
    public Similarity get() {
        return similarity;
    }
    

}
