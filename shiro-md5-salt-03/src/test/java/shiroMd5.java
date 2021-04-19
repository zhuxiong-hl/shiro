import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class shiroMd5 {
    /**
     * 202cb962ac59075b964b07152d234b70
     * 8a83592a02263bfe6752b2b5b03a4799
     * e4f9bf3e0c58f045e62c23c533fcf633
     */
    @Test
    public void test01(){
        //  使用MD5
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex());

        //  使用MD5 + salt处理
        Md5Hash md5Hash1 = new Md5Hash("123", "X0*7ps");
        System.out.println(md5Hash1.toString());

        //  使用MD5 + salt + hash散列（参数代表要散列多少次，一般是1024或2048）
        Md5Hash md5Hash2 = new Md5Hash("123", "X0*7ps", 1024);
        System.out.println(md5Hash2.toHex());

    }
}
