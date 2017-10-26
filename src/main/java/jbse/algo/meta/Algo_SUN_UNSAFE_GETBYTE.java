package jbse.algo.meta;

import static jbse.algo.Util.unsafe;

import java.util.function.Supplier;

import jbse.algo.Algo_INVOKEMETA_Nonbranching;
import jbse.algo.exc.SymbolicValueNotAllowedException;
import jbse.mem.State;
import jbse.mem.exc.ThreadStackEmptyException;
import jbse.val.Simplex;

/**
 * Meta-level implementation of {@link sun.misc.Unsafe#getByte(long)}.
 * 
 * @author Pietro Braione
 */
public final class Algo_SUN_UNSAFE_GETBYTE extends Algo_INVOKEMETA_Nonbranching {
    private byte value; //set by cookMore
    
    @Override
    protected Supplier<Integer> numOperands() {
        return () -> 2;
    }
    
    @Override
    protected void cookMore(State state) throws SymbolicValueNotAllowedException {
        if (!(this.data.operand(1) instanceof Simplex)) {
            throw new SymbolicValueNotAllowedException("sun.misc.Unsafe.getByte cannot be invoked with a symbolic argument");
        }
        final long memoryAddress = ((Long) ((Simplex) this.data.operand(1)).getActualValue()).longValue();
        this.value = unsafe().getByte(memoryAddress);
    }
    
    @Override
    protected void update(State state) throws ThreadStackEmptyException {
        state.pushOperand(state.getCalculator().valInt(this.value));
    }
}
