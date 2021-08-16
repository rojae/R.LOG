package kr.or.rlog.common;

import kr.or.rlog.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Attribute;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class TileViewPreparer implements ViewPreparer {

    @Autowired
    private CategoryService categoryService;

    public void execute(Request tilesRequest, AttributeContext attributeContext)
            throws PreparerException {
        log.info("TileViewPreparer :: Tile menu called");
        attributeContext.putAttribute( "menu",  new Attribute(categoryService.createRoot()), true);
        log.info("TileViewPreparer :: Tile menu end");
    }
}