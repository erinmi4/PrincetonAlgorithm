public class HelloGoodbye {
    public static void main(String[] args) {
        // 检查参数数量是否正确
        if (args.length < 2) {
            System.out.println("Maybe you lost some arguments");
            return; // 终止程序执行
        }

        // 打印hello信息
        System.out.println("Hello " + args[0] + " and " + args[1]);

        // bye
        System.out.println("HelloGoodbye " + args[1] + " and " + args[0]);
    }
}


