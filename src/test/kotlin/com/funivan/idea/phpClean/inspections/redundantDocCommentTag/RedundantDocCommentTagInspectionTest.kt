package com.funivan.idea.phpClean.inspections.redundantDocCommentTag

import com.funivan.idea.phpClean.BaseInspectionTest

class RedundantDocCommentTagInspectionTest : BaseInspectionTest() {
    fun testRedundantReturnType() {
        assert(
                RedundantDocCommentTagInspection(),
                """
                    <?php
                     /**
                      *<warning descr="Redundant PhpDoc tag">@return void</warning>
                      */
                     function show(string ${'$'}message):void {}
                    """,
                """
                    <?php
                     /**
                      *
                      */
                     function show(string ${'$'}message):void {}
                    """
        )
    }

    fun testRedundantParameterTag() {
        assert(
                RedundantDocCommentTagInspection(),
                """
                    <?php
                     /**
                      *<warning descr="Redundant PhpDoc tag">@param string ${'$'}message</warning>
                      */
                     function show(int ${'$'}a, string ${'$'}message):void {}
                    """
        )
    }
}