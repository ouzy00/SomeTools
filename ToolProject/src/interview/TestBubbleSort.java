package interview;

/**
 * 测试冒泡排序工具类
 * @author OUZY
 *
 */
public class TestBubbleSort {
	
	public static void main(String[] args) {
		int[] array = {72, 54, 59, 30, 31, 78, 2, 77, 82, 72};
		bubbleSort1(array);
	}

	/**
	 * 冒泡拍讯实现方法
	 * 实现原理：比较相邻的两个元素，每次比较完毕最大的一个数字字跑到本轮的末尾。
	 * 1.第一轮比较相邻两个元素，如果左边元素大于右边元素，则交换。
	 * 2.经过第一轮比较，最大的元素跑到了最后一个，所以第二轮比较，最后一个元素不需要进行比较了。
	 *   第二轮还是从索引0和1开始比较，只是不需要比较最后一个了，算法还是一样的。第三轮、第四轮以此类推。
	 * @param array
	 */
	public static void bubbleSort(int[] array) {
	    int len = array.length; //获取数组长度 10
	    for (int i = 0; i < len; i++) { //循环10轮遍历数组，第一轮比较9次，第二轮比较8次，第三轮比较7次...
	        for (int j = 0; j < len - i - 1; j++) {
	            if (array[j] > array[j + 1]) { //第[0]个与后一个即第[1]个比较，大的在右，小的在左，此时数组为：54, 72, 59, 30, 31, 78, 2, 77, 82, 72
	            							   //第[1]个与后一个即第[2]个比较，大的在右，小的在左，此时数组为：54, 59, 72, 30, 31, 78, 2, 77, 82, 72
	            							   //...
	                int temp = array[j + 1];
	                array[j + 1] = array[j];
	                array[j] = temp;
	            }
	        }
	        System.out.println("------------------");
	    }
	    for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
	
	/**
	 * 冒泡排序方法实现--优化
	 * @param array
	 */
	public static void bubbleSort1(int[] array) {
	    int len = array.length;
	    boolean flag = true;
	    while (flag) {
	        flag = false;
	        for (int i = 0; i < len - 1; i++) {
	            if (array[i] > array[i + 1]) {
	                int temp = array[i + 1];
	                array[i + 1] = array[i];
	                array[i] = temp;
	                flag = true;
	            }
	        }
	        len--;
	    }
	    for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}


}
