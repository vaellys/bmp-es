package com.ist.assemble;

import org.apache.lucene.search.similarities.DefaultSimilarity;

/**
 * 自定义相似度
 * @author qianguobing
 *
 */
public class CustomSimilarity extends DefaultSimilarity {
    @Override
    public float idf(long docFreq, long numDocs) {
        return 1.0f;
    }
}
