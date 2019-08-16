package cn.mou.annotation;

import org.junit.Test;

/**
 * @author: mou
 * @date: 2019-08-07
 */
public class AnnotationTest {

    @Test
    public void testAnnotation() {
        UserInfoRequest request = new UserInfoRequest();
        try {
            AnnotationChecker.checkParam(request);
        } catch (PostingException e) {
            System.out.println(e.getErrorCode() + ":" + e.getErrorMessage());
        }
    }
}
