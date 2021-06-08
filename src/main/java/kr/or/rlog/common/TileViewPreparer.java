package kr.or.rlog.common;

import kr.or.rlog.category.CategoryService;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TileViewPreparer implements ViewPreparer {

    @Autowired
    private CategoryService categoryService;

    public void execute(Request tilesRequest, AttributeContext attributeContext)
            throws PreparerException {
        attributeContext.putAttribute( "menu",  new Attribute(categoryService.createRoot()), true);
    }
}