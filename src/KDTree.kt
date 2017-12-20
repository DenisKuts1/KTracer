/**
 * Created by denis on 16.12.2017.
 */
class KDTree {
    private val objectNodes: ArrayList<Node> = ArrayList()
    private val scene: Scene

    constructor(scene: Scene) {
        this.scene = scene
        createTree()
    }

    fun getBoxes(ray: Ray): ArrayList<Box> {
        val boxes = ArrayList<Box>()
        for (node in objectNodes)
            node.intersection(ray, boxes)
        return boxes
    }

    private fun createTree() {
        for (obj in scene.objects) {
            objectNodes += createObjTree(obj)
        }
    }


    private fun createObjTree(obj: Obj): Node {
        val box = Box()
        for (triangle in obj.triangles) {
            box += triangle
        }
        val node = Node(box)
        recursiveNode(node, 1, 0)

        return node
    }

    private fun generateBoxes(node: Node): Pair<Box, Box>? {
        val cost = node.box.volume() * node.box.triangles.size
        val min = node.box.min
        val max = node.box.max
        val axis = floatArrayOf(
                (max[0] - min[0]) / 10f,
                (max[1] - min[1]) / 10f,
                (max[2] - min[2]) / 10f)
        var pair: Pair<Box, Box>? = null
        var minWeight = Float.POSITIVE_INFINITY
        for (j in 0..2) {
            for (i in 1..9) {
                val rightMin = Point3D(min[0], min[1], min[2])
                val leftMax = Point3D(max[0], max[1], max[2])
                leftMax[j] -= axis[j] * (10 - i)
                rightMin[j] += axis[j] * i
                val left = Box(min, leftMax)
                val right = Box(rightMin, max)
                val weight = weight(left, right, node.box.triangles)
                if (weight < minWeight) {
                    minWeight = weight
                    pair = Pair(left, right)
                }
            }
        }
        if (cost > minWeight) {
            return pair!!
        }
        return null

    }

    private fun weight(left: Box, right: Box, triangles: ArrayList<Triangle>): Float {
        var leftCounter = 0
        var rightCounter = 0
        for (triangle in triangles) {
            if (triangle in left) {
                leftCounter++
            }
            if (triangle in right) {
                rightCounter++
            }
        }
        return left.volume() * leftCounter + right.volume() * rightCounter
    }

    private fun recursiveNode(node: Node, limit: Int, counter: Int) {
        node.left = null
        node.right = null
        if (node.box.size() <= limit || counter > 15) {
            return
        }
        /*val min = node.box.min
        val max = node.box.max
        val center = node.box.center
        val xAxis = max["x"] - min["x"]
        val yAxis = max["y"] - min["y"]
        val zAxis = max["z"] - min["z"]
        val orientation = if (xAxis > yAxis) {
            if (xAxis > zAxis) {
                0
            } else {
                2
            }
        } else {
            if (yAxis > zAxis) {
                1
            } else {
                2
            }
        }
        val leftMin: Point3D
        val leftMax: Point3D
        val rightMin: Point3D
        val rightMax: Point3D
        when (orientation) {
            0 -> {
                leftMin = Point3D(min[0], min[1], min[2])
                leftMax = Point3D(center[0], max[1], max[2])
                rightMin = Point3D(center[0], min[1], min[2])
                rightMax = Point3D(max[0], max[1], max[2])
            }
            1 -> {
                leftMin = Point3D(min[0], min[1], min[2])
                leftMax = Point3D(max[0], center[1], max[2])
                rightMin = Point3D(min[0], center[1], min[2])
                rightMax = Point3D(max[0], max[1], max[2])
            }
            else -> {
                leftMin = Point3D(min[0], min[1], min[2])
                leftMax = Point3D(max[0], max[1], center[2])
                rightMin = Point3D(min[0], min[1], center[2])
                rightMax = Point3D(max[0], max[1], max[2])
            }
        }
        val leftBox = Box(leftMin, leftMax)
        val rightBox = Box(rightMin, rightMax)*/

        val pair = generateBoxes(node)
        if (pair == null) return
        val leftBox = pair.first
        val rightBox = pair.second

        val leftTriangles = ArrayList<Triangle>()
        val rightTriangles = ArrayList<Triangle>()
        for (triangle in node.box.triangles) {
            if (triangle in leftBox) leftTriangles += triangle
            if (triangle in rightBox) rightTriangles += triangle
        }

        if (leftTriangles.isNotEmpty()) {
            leftBox += leftTriangles
            val leftNode = Node(leftBox)
            node.left = leftNode
            recursiveNode(leftNode, limit, counter + 1)
        } else {
            node.left = null
        }
        if (rightTriangles.isNotEmpty()) {
            rightBox += rightTriangles
            val rightNode = Node(rightBox)
            node.right = rightNode
            recursiveNode(rightNode, limit, counter + 1)
        } else {
            node.right = null
        }
    }


    inner class Node(val box: Box) {
        var left: Node? = null
        var right: Node? = null

        fun intersection(ray: Ray, boxes: ArrayList<Box>) {
            if (box.intersect(ray, 0f, 10000f)) {
                if (left == null && right == null) {
                    boxes.add(box)
                } else {
                    if (left != null) {
                        left?.intersection(ray, boxes)
                    }
                    if (right != null) {
                        right?.intersection(ray, boxes)
                    }
                }
            }
        }

    }
}
