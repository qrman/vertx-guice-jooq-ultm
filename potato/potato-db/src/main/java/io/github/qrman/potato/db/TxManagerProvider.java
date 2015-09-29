package io.github.qrman.potato.db;

import com.github.witoldsz.ultm.TxManager;
import com.github.witoldsz.ultm.ULTM;
import javax.inject.Inject;
import javax.inject.Provider;

public class TxManagerProvider implements Provider<TxManager> {

    private final ULTM ultm;

    @Inject
    public TxManagerProvider(ULTM ultm) {
        this.ultm = ultm;
    }

    @Override
    public TxManager get() {
        return ultm.getTxManager();
    }
}
