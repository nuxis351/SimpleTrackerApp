package nuxis351.github.com.simpletracker;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;

/**
 * Created by nuxis on 4/23/2019.
 */

public class TabInfo {
    private CharSequence[] texts;
    private Drawable[] icons;
    private int count;

    public TabInfo(TabLayout tabLayout){
        this.count = tabLayout.getTabCount();
        this.texts = new CharSequence[count];
        this.icons = new Drawable[count];
        for(int i = 0; i < count; i++){
            this.icons[i] = tabLayout.getTabAt(i).getIcon();
            this.texts[i] = tabLayout.getTabAt(i).getText();
        }
    }

    public void setTabIcons(final TabLayout tabLayout){
        class TabTextsTask extends TabTask {
            public TabTextsTask(int count) { super(count); }

            public void calledMethod() {
                tabLayout.getTabAt(count).setIcon(icons[count]);
            }
        }
        itterate(new TabTextsTask(this.count-1));
    }

    public void setTabTexts(final TabLayout tabLayout){
        class TabTextsTask extends TabTask {
            public TabTextsTask(int count) { super(count); }

            public void calledMethod() {
                tabLayout.getTabAt(count).setText(texts[count]);
            }
        }
        itterate(new TabTextsTask(this.count-1));
    }
    public void itterate(Runnable block){
        for(int i = 0; i < this.count; i++){
            block.run();
        }
    }

   private abstract class TabTask implements Runnable {
        int count;
        TabTask(int count){
            this.count = count;
        }

       @Override
       public void run() {
           calledMethod();
           count--;
       }
       public abstract void calledMethod();
   }
}
