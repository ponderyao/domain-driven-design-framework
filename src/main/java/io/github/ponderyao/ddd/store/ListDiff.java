package io.github.ponderyao.ddd.store;

import java.util.ArrayList;

/**
 * ListDiff：EntityDiff 列表
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ListDiff extends ArrayList<EntityDiff> implements Diff {

    private static final long serialVersionUID = 4540981974758745279L;

    @Override
    public DiffType getType() {
        return null;
    }
    
}
