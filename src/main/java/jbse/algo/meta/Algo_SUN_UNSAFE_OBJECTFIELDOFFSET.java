package jbse.algo.meta;

import static jbse.algo.Util.exitFromAlgorithm;
import static jbse.algo.Util.failExecution;
import static jbse.algo.Util.throwNew;
import static jbse.algo.Util.throwVerifyError;
import static jbse.bc.Signatures.INTERNAL_ERROR;
import static jbse.bc.Signatures.JAVA_FIELD_CLAZZ;
import static jbse.bc.Signatures.JAVA_FIELD_SLOT;
import static jbse.bc.Signatures.NULL_POINTER_EXCEPTION;
import static jbse.common.Type.LONG;

import java.util.function.Supplier;

import jbse.algo.Algo_INVOKEMETA_Nonbranching;
import jbse.algo.InterruptException;
import jbse.algo.StrategyUpdate;
import jbse.algo.exc.CannotManageStateException;
import jbse.algo.exc.SymbolicValueNotAllowedException;
import jbse.bc.ClassFile;
import jbse.bc.Signature;
import jbse.common.exc.ClasspathException;
import jbse.dec.exc.DecisionException;
import jbse.mem.Instance;
import jbse.mem.Instance_JAVA_CLASS;
import jbse.mem.State;
import jbse.mem.exc.FrozenStateException;
import jbse.mem.exc.ThreadStackEmptyException;
import jbse.tree.DecisionAlternative_NONE;
import jbse.val.Calculator;
import jbse.val.Reference;
import jbse.val.Simplex;
import jbse.val.exc.InvalidOperandException;
import jbse.val.exc.InvalidTypeException;

public final class Algo_SUN_UNSAFE_OBJECTFIELDOFFSET extends Algo_INVOKEMETA_Nonbranching {
    private Simplex ofst; //set by cookMore

    @Override
    protected Supplier<Integer> numOperands() {
        return () -> 2;
    }

    @Override
    protected void cookMore(State state)
    throws ThreadStackEmptyException, DecisionException, ClasspathException,
    CannotManageStateException, InterruptException, FrozenStateException, 
    InvalidTypeException, InvalidOperandException {
    	final Calculator calc = this.ctx.getCalculator();
        try {
            //gets the field object
            final Reference fldRef = (Reference) this.data.operand(1);
            if (state.isNull(fldRef)) {
                throwNew(state, calc, NULL_POINTER_EXCEPTION); //TODO is it ok???
                exitFromAlgorithm();
            }
            final Instance fldInstance = (Instance) state.getObject(fldRef);
            if (fldInstance.isSymbolic()) {
                throw new SymbolicValueNotAllowedException("The Field f parameter to invocation of method sun.misc.Unsafe.objectFieldOffset must not be symbolic.");
            }
            
            //gets the class of the field
            final Reference fldClassInstanceRef = (Reference) fldInstance.getFieldValue(JAVA_FIELD_CLAZZ);
            final Instance_JAVA_CLASS fldClassInstance = (Instance_JAVA_CLASS) state.getObject(fldClassInstanceRef);
            final ClassFile fldClassFile = fldClassInstance.representedClass();
            
            //gets the slot
            final Simplex _slot = (Simplex) fldInstance.getFieldValue(JAVA_FIELD_SLOT);
            if (_slot.isSymbolic()) {
                throw new SymbolicValueNotAllowedException("The slot field in a java.lang.reflect.Field object is symbolic.");
            }
            final int slot = ((Integer) ((Simplex) _slot).getActualValue()).intValue();
            
            //gets the field signature
            final Signature[] declaredFields = fldClassFile.getDeclaredFields();
            if (slot < 0 || slot >= declaredFields.length) {
                //invalid slot number
                throwNew(state, calc, INTERNAL_ERROR);
                exitFromAlgorithm();
            }            
            final Signature fieldSignature = declaredFields[slot];
            
            //gets the offset by scanning the fields list of the 
            //object backwards
            //TODO this calculation should be encapsulated somewhere in ObjektImpl, or all the logic here and in ObjektImpl should be moved in ClassHierarchy or in ClassFile.
            final Signature[] allFields = state.getClassHierarchy().getAllFields(fldClassFile);
            boolean found = false;
            for (int _ofst = 0; _ofst < allFields.length; ++_ofst) {
                if (allFields[allFields.length - 1 - _ofst].equals(fieldSignature)) {
                    found = true;
                    this.ofst = (Simplex) calc.pushInt(_ofst).to(LONG).pop();
                    break;
                }
            }
            if (!found) {
                //this should never happen
                failExecution("Declared field " + fieldSignature + " not found in the list of all fields of an object with class " + fldClassFile.getClassName() + ".");
            }
        } catch (ClassCastException e) {
            throwVerifyError(state, calc);
            exitFromAlgorithm();
        }
        //TODO check that operands are concrete and kill trace if they are not
    }

    @Override
    protected StrategyUpdate<DecisionAlternative_NONE> updater() {
        return (state, alt) -> {
            state.pushOperand(this.ofst);
        };
    }
}
