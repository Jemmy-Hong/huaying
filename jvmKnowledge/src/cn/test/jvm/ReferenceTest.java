package cn.test.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {

	public static void main(String[] args) {
		//�ڶ��д���һ������Obj
		//��ջ�д���һ��p�������ô˶���Obj
		Person p = new Person(1);
		
		//��ջ�д���һ��softReference�������ô˶���Obj,���Ի�ȡ���������ֵ
		//SoftReference<Person> softReference = new SoftReference<Person>(p);		
		/*p = null; //�Ͽ�p��Obj��ǿ����
		//System.out.println(p.getId());
		System.gc();
		System.out.println(softReference.get().getId());*///���Ի��,��û�л���,��ʹʹ����������
		
		
		//��ջ�д���һ��weakReference�������ô˶���Obj ���Ի�ȡ���������ֵ
		/*WeakReference<Person> weakReference = new WeakReference<Person>(p);
		p = null;
		System.out.println(weakReference.get().getId());
		System.gc();
		System.out.println(weakReference.get().getId());*///��������֮��,�����ý�������
	
		
		/**
		 *�����õ���������,������󱻻���������ʱ,�յ�һ��ϵͳ֪ͨ 
		 */
		ReferenceQueue<Person>  referenceQueue = new ReferenceQueue<>();
		//��ջ�д���һ��phantomReference�������ô˶���Obj, �����Ի�ȡ���������ֵ
		PhantomReference<Person> phantomReference = new PhantomReference<Person>(p, referenceQueue);
		//System.out.println(phantomReference.get().getId()); //��ȡ����
		
		//����ǰ
		System.out.println(referenceQueue.poll());
		
		//���պ�
		p = null;
		System.gc(); //��������֮�����referenceQueue�з���Ϣ
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(referenceQueue.poll());
	}

}

class Person{
	
	public Person(Integer id) {
		this.id = id;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*@Override
	protected void finalize() throws Throwable {
		System.out.println("finalized!!!");
	}*/
	
}

