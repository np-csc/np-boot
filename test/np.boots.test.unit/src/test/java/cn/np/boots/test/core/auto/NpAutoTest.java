package cn.np.boots.test.core.auto;

import cn.np.boots.core.spring.auto.EnableNpAutoAfterApplicationStarted;
import cn.np.boots.core.spring.auto.configurer.NpSpringAutoAfterApplicationStartedConfigurer;
import cn.np.boots.test.common.BaseTest;
import org.junit.Test;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

@EnableNpAutoAfterApplicationStarted(action = SpringAutoApplicationStarted.class)
public class NpAutoTest extends BaseTest {
    @Test
    public void test() {

    }
}

class SpringAutoApplicationStarted extends NpSpringAutoAfterApplicationStartedConfigurer {
    @Override
    public void doAction() {
//        this.annotationMetadata.getAnnotations().stream(EnableNpAutoAfterApplicationStarted.class).forEach(x->{
//            System.out.println(x);
//        });
        MergedAnnotation<?> mergedAnnotation = this.annotation;
        AnnotationMetadata annotationMetadata = this.annotationMetadata;

        this.annotationMetadata.getAnnotations().stream(EnableNpAutoAfterApplicationStarted.class).forEach(x->{
            System.out.println(x);
        });
    }
}
