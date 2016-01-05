package com.ist.ioc.service.common.elasticsearch.impl.commonPool;

import static org.junit.Assert.fail;
import io.searchbox.client.JestClient;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/applicationContext-es.xml" })
public class PooledObjectFactoryTest {
    private Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private GenericObjectPool<JestClient> genericObjectPool;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBorrowObject() {
        JestClient bo = null;
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("\n===========" + i + "===========");
                System.out.println("池中处于闲置状态的实例pool.getNumIdle()：" + genericObjectPool.getNumIdle());
                // 从池里取一个对象，新创建makeObject或将以前闲置的对象取出来
                bo = genericObjectPool.borrowObject();
                System.out.println("bo:" + bo);
                System.out.println("池中所有在用实例数量pool.getNumActive()：" + genericObjectPool.getNumActive());
                if ((i % 2) == 0) {
                    // 用完之后归还对象
                    genericObjectPool.returnObject(bo);
                    System.out.println("归还对象！！！！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bo != null) {
                    genericObjectPool.returnObject(bo);
                }
                // 关闭池
                genericObjectPool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
