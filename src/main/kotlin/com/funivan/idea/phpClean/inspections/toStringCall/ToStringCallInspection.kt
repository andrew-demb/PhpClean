package com.funivan.idea.phpClean.inspections.toStringCall

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.NewExpression
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor


class ToStringCallInspection : PhpInspection() {
    val context = IsToStringContext()
    override fun getShortName() = "ToStringCallInspection"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PhpElementVisitor() {
            override fun visitPhpVariable(variable: Variable) {
                if (context.match(variable.parent)) {
                    if (IsSingleClassType().match(variable)) {
                        holder.registerProblem(
                                variable,
                                "Deprecated __toString call"
                        )
                    }
                }
            }

            override fun visitPhpNewExpression(expression: NewExpression) {
                val parent = expression.parent
                if (context.match(parent)) {
                    holder.registerProblem(
                            expression,
                            "Deprecated __toString call"
                    )
                }
            }

            override fun visitPhpMethodReference(reference: MethodReference) {
                if (context.match(reference.parent)) {
                    val resolve = reference.resolve()
                    if (resolve is Method) {
                        val type = resolve.returnType
                        var safeType = false
                        if (type == null || listOf("string", "int", "float").contains(type.text)) {
                            safeType = true
                        }
                        if (!safeType) {
                            holder.registerProblem(
                                    reference,
                                    "Deprecated __toString call"
                            )
                        }
                    }
                }
            }
        }
    }
}