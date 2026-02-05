package h10.jagr;

import org.junit.jupiter.api.extension.ExtendWith;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;

import java.util.Set;

/**
 * A {@link h10.MySet} class transformer which removes the validation of the MySet class in the constructor since
 * the test will use the underlying comparator to track which elements are visited. The validation will fake
 * the visitation of the elements since both methods will visit all elements in the list.
 *
 * <p>Do not forget to annotate all tests using this transformer with the annotation {@link ExtendWith} with the class type
 * {@link JagrExecutionCondition}.
 *
 * <p>Remember that the test cases will only work if the Jagr is present. If you want to run the test cases without
 * the Jagr, you can remove the validation check in the constructor of {@link h10.MySet} (isOrdered and
 * isPairwiseDifferent).
 *
 * @author Nhan Huynh
 * @see h10.MySet
 */
public class MySetValidationTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h10/MySet")) {
            reader.accept(new MySetVisitor(Opcodes.ASM9, writer), 0);
            return;
        }
        reader.accept(writer, 0);
    }

    /**
     * Class visitor that delegates to {@link ValidationVisitor} for methods in the MySet class.
     */
    public static class MySetVisitor extends ClassVisitor {

        /**
         * Creates a new {@link MySetVisitor}.
         *
         * @param api          the ASM API version
         * @param classVisitor the class visitor to delegate to
         */
        public MySetVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new ValidationVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }

    /**
     * Method visitor that replaces calls to methods that should be ignored with a op code {@value Opcodes#ICONST_1}
     * (as if the method returned true).
     */
    private static class ValidationVisitor extends MethodVisitor {

        /**
         * Methods that should be ignored when validating the MySet class.
         */
        private static final Set<String> IGNORED_METHOD_CALLS = Set.of(
            "isOrdered",
            "isPairwiseDifferent"
        );

        /**
         * Creates a new {@link ValidationVisitor}.
         *
         * @param api           the ASM API version
         * @param methodVisitor the method visitor to delegate to
         */
        public ValidationVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (IGNORED_METHOD_CALLS.contains(name)) {
                // Remove the three arguments of the initial method call (this + 2 normal) from the stack
                visitInsn(Opcodes.POP);
                visitInsn(Opcodes.POP);
                visitInsn(Opcodes.POP);
                // Replace with constant 1 (as if the method returned true)
                visitInsn(Opcodes.ICONST_1);
                return;
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
